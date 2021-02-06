package org.ligi.tracedroid.sending

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import org.ligi.tracedroid.TraceDroid.deleteStacktraceFiles
import org.ligi.tracedroid.TraceDroid.getStackTraceText
import org.ligi.tracedroid.TraceDroid.stackTraceFiles
import org.ligi.tracedroid.TraceDroid.traceDroidMetaInfo

fun sendTraceDroidStackTracesIfExist(email: String, context: Context): Boolean {
    if (stackTraceFiles.isNotEmpty()) {
        AlertDialog.Builder(context).setTitle("Problem Report")
                .setMessage("A Problem with this App was detected - do you want to send a Crash-Report to help fix this Problem?")
                .setPositiveButton("Send") { _, _ ->
                    val emailIntent = Intent(Intent.ACTION_SEND)
                    emailIntent.type = "plain/text"
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "[TraceDroid Report] " + traceDroidMetaInfo.appPackageName)
                    emailIntent.putExtra(Intent.EXTRA_TEXT, getStackTraceText(10))
                    context.startActivity(Intent.createChooser(emailIntent, "Send mail..."))
                    deleteStacktraceFiles()
                }
                .setNegativeButton("No") { _, _ -> deleteStacktraceFiles() }
                .setNeutralButton("Later") { _, _ -> }
                .show()
        return true
    }
    return false
}
