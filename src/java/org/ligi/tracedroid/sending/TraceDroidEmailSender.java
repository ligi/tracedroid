
package org.ligi.tracedroid.sending;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import org.ligi.tracedroid.TraceDroid;
import org.ligi.tracedroid.collecting.TraceDroidMetaInfo;

public class TraceDroidEmailSender {

    public static boolean sendStackTraces(final String email, final Context context) {
        if (TraceDroid.getStackTraceFiles().length > 0) {
            new AlertDialog.Builder(context).setTitle("Problem Report")

                    .setMessage("A Problem with this App was detected - would you send an Crash-Report to help fixing this Problem?")
                    .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                            emailIntent.setType("plain/text");
                            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{email});
                            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "[TraceDroid Report] " + TraceDroidMetaInfo.getAppPackageName());
                            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, TraceDroid.getStackTraceText(10));
                            context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                            TraceDroid.deleteStacktraceFiles();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    TraceDroid.deleteStacktraceFiles();
                }
            }).setNeutralButton("Later", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                }
            })

                    .show();


            return true;
        }
        return false;
    }
}
