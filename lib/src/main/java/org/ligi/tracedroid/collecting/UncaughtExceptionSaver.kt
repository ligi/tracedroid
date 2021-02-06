package org.ligi.tracedroid.collecting

import org.ligi.tracedroid.logging.BufferedLogTree
import timber.log.Timber
import java.io.BufferedWriter
import java.io.FileWriter
import java.io.PrintWriter
import java.io.StringWriter

internal class UncaughtExceptionSaver(
        private val traceDroidMetaInfo: TraceDroidMetaInfo,
        private var oldHandler: Thread.UncaughtExceptionHandler,
        private val bufferedLogTree: BufferedLogTree) : Thread.UncaughtExceptionHandler {

    override fun uncaughtException(thread: Thread, throwable: Throwable) {

        try {
            // version and timestamp for filename
            // TODO check if additional entropy like some random is needed here
            val filename = (traceDroidMetaInfo.filesPath + "/" + traceDroidMetaInfo.appVersion + "-" + System.currentTimeMillis() + traceDroidMetaInfo.fileSuffix)
            Timber.d("Writing unhandled exception to: $filename")
            // Write the stacktrace to disk
            BufferedWriter(FileWriter(filename)).use {
                it.write("Android Version: ${traceDroidMetaInfo.androidVersion}\n")
                it.write("Phone Model: ${traceDroidMetaInfo.phoneModel}\\n")
                it.write("TraceDroid Version: ${traceDroidMetaInfo.traceDroidVersion}\\n")
                it.write("Stacktrace: ${getThrowableStackAsString(throwable)}\\n")
                it.write("Log:  ${bufferedLogTree.buffer.joinToString("\n")}")
                it.flush()
            }

        } catch (e: Exception) {
            // Nothing much we can do about this - the game is over
            Timber.e(e, "Error saving exception stacktrace")
        }
        Timber.d(getThrowableStackAsString(throwable))

        //call original handler
        oldHandler.uncaughtException(thread, throwable)
    }

    companion object {
        fun getThrowableStackAsString(t: Throwable): String {
            return try {
                val sw = StringWriter()
                t.printStackTrace(PrintWriter(sw))
                sw.toString()
            } catch (e2: Exception) {
                "bad exception stack"
            }
        }
    }
}