package org.ligi.tracedroid.logging

import timber.log.Timber
import android.util.Log.ERROR
import android.util.Log.INFO
import android.util.Log.WARN
import java.util.ArrayDeque

internal class BufferedLogTree(private val bufferSize: Int = 200) : Timber.Tree() {

    val buffer = ArrayDeque<String>(bufferSize + 1)

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {

        val log = buildString(message.length + 16) {
            append(when (priority) {
                INFO -> "I"
                WARN -> "W"
                ERROR -> "E"
                else -> priority.toString()
            })
            append(' ')
            append(message)
            t?.let {
                append(" throwable: ")
                append(it.message)
            }
        }
        // TODO replace with lock free?
        synchronized(buffer) {
            buffer.addLast(log)
            if (buffer.size > bufferSize) {
                buffer.removeFirst()
            }
        }
    }
}

