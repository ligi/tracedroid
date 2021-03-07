package org.ligi.tracedroid

import android.content.Context
import org.ligi.tracedroid.collecting.TraceDroidMetaInfo
import org.ligi.tracedroid.collecting.UncaughtExceptionSaver
import org.ligi.tracedroid.collecting.createMetaInfo
import org.ligi.tracedroid.logging.BufferedLogTree
import timber.log.Timber
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.FilenameFilter

object TraceDroid {

    lateinit var traceDroidMetaInfo: TraceDroidMetaInfo

    private val bufferedLogTree by lazy { BufferedLogTree() }
    /**
     * registers a UncaughtException handler which saves the stacktrace with
     * some meta-information to the filesystem
     *
     * @param context - the context
     */
    fun init(context: Context) {

        traceDroidMetaInfo = createMetaInfo(context)

        Timber.plant(bufferedLogTree)
        // get the current handler
        val currentHandler = Thread.getDefaultUncaughtExceptionHandler()
        if (currentHandler != null) {
            Timber.d("current handler class=" + currentHandler.javaClass.name)


            // register our saving handler if not already registered
            if (currentHandler !is UncaughtExceptionSaver) {
                // Register default exceptions handler
                val exceptionSaver = UncaughtExceptionSaver(traceDroidMetaInfo,currentHandler,bufferedLogTree)
                Thread.setDefaultUncaughtExceptionHandler(exceptionSaver)
            }
        }
    }// Try to create the files folder if it doesn't exist

    /**
     * get the dir for the stacktraces and ensure it exists
     *
     * @return
     */
    private val ensuredTraceDir: File
        get() {
            val traceFilesDir = File(traceDroidMetaInfo.filesPath)

            // Try to create the files folder if it doesn't exist
            if (!traceFilesDir.exists()) {
                traceFilesDir.mkdirs()
            }
            return traceFilesDir
        }

    /**
     * get all Stacktrace Files
     *
     * @return all stacktrace files
     */
    val stackTraceFiles: Array<File>
        get() {
            val filter = FilenameFilter { _, name -> name.endsWith(traceDroidMetaInfo.fileSuffix) }
            return ensuredTraceDir.listFiles(filter) ?: emptyArray()
        }

    fun getStackTraceText(limit: Int): String {
        var linesLeft = limit
        Timber.d("Searching Exceptions in: " + traceDroidMetaInfo.filesPath)
        val stackTraceText = StringBuilder()
        for (act_file in stackTraceFiles) {
            // limit the number of stack traces
            if (linesLeft-- > 0) {
                try {
                    stackTraceText.append("file: ").append(act_file.toString())
                    stackTraceText.append(System.getProperty("line.separator"))
                    if (linesLeft-- > 0) {
                        // Read contents of stacktrace file
                        val input = BufferedReader(
                                FileReader(act_file))
                        input.use { usedInput ->
                            usedInput.readLines().forEach { line ->
                                stackTraceText.append(line)
                                stackTraceText.append(System.getProperty("line.separator"))
                            }
                        }
                    } else stackTraceText.append(" discarded by limit")
                } catch (e: Exception) {
                    Timber.w(e, "problem loading stacktrace")
                }
            }
        }
        return stackTraceText.toString()
    }

    fun getLog() = bufferedLogTree.buffer

    fun deleteStacktraceFiles() {
        for (act_file in stackTraceFiles) {
            act_file.delete()
        }
    }
}