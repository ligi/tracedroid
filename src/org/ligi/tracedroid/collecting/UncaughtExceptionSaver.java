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
package org.ligi.tracedroid.collecting;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;

import org.ligi.tracedroid.logging.Log;

public class UncaughtExceptionSaver implements UncaughtExceptionHandler {
	UncaughtExceptionHandler oldHandler;
	
	public UncaughtExceptionSaver(UncaughtExceptionHandler oldHandler ){
		this.oldHandler=oldHandler;
	}
	
	@Override
	public void uncaughtException(Thread thread, Throwable throwable) {
		
		try {
			// version and timestamp for filename 
			// TODO check if additional entropy like some random is needed here
			String filename = TraceDroidMetaInfo.getFilesPath()+"/" 
							+ TraceDroidMetaInfo.getAppVersion() 
							+ "-"+System.currentTimeMillis()
							+ TraceDroidMetaInfo.getFileSuffix();
			
			Log.d("Writing unhandled exception to: " +filename);
			// Write the stacktrace to disk
			BufferedWriter bos = new BufferedWriter(new FileWriter(filename));
			bos.write("Android Version: " + TraceDroidMetaInfo.getAndroidVersion()+ "\n");
			bos.write("Phone Model: " + TraceDroidMetaInfo.getPhoneModel() + "\n");
			bos.write("Stacktrace \n: "+ getThrowableStackAsString(throwable));
			bos.flush();
			bos.close();

		} catch (Exception ebos) {
			// Nothing much we can do about this - the game is over
			Log.e( "Error saving exception stacktrace", ebos);
		}
		
		Log.d(getThrowableStackAsString(throwable));

		//call original handler
		oldHandler.uncaughtException(thread, throwable);
	}


	 public static String getThrowableStackAsString(Throwable t) {
	  try {
	    StringWriter sw = new StringWriter();
	    t.printStackTrace(new PrintWriter(sw));
	    return sw.toString();
	  }
	  catch(Exception e2) {
	    return "bad exception stack";
	  }
	 }
}
