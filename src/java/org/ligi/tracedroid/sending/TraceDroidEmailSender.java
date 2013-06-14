/**
 * tracedroid 
 * by Marcus -Ligi- Bueschleb 
 * http://ligi.de
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3 as 
 * published by the Free Software Foundation; 
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details. 
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 * 
 **/
package org.ligi.tracedroid.sending;

import org.ligi.tracedroid.TraceDroid;
import org.ligi.tracedroid.collecting.TraceDroidMetaInfo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

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
