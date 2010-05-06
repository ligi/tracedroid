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
package org.ligi.tracedroid.logging;

public class Log {

	private static String TAG="TraceDroid";

	// shortcuts with a predefined tag
	
	public final static void e( String msg ) {
		e(TAG,msg);
	}

	public final static void e( String msg , Throwable tr ) {
		e(TAG,msg,tr);
	}

	public final static void w( String msg ) {
		w(TAG,msg);
	}

	public final static void w( String msg , Throwable tr ) {
		w(TAG,msg,tr);
	}
	
	public final static void i( String msg ) {
		i(TAG,msg);
	}

	public final static void i( String msg , Throwable tr ) {
		i(TAG,msg,tr);
	}

	
	public final static void d( String msg ) {
		d(TAG,msg);
	}

	public final static void d( String msg , Throwable tr ) {
		d(TAG,msg,tr);
	}

	// mappers from this Log to android Util Log
	
	public final static void e( String tag, String msg ) {
		android.util.Log.e(tag,msg);
	}
	
	public final static void e( String tag, String msg , Throwable tr ) {
		android.util.Log.e(tag,msg,tr);
	}

	public final static void w(String tag, String msg ) {
		android.util.Log.w(tag,msg);
	}

	public final static void w( String tag,String msg , Throwable tr ) {
		android.util.Log.w(tag,msg,tr);
	}
	
	public final static void i( String tag, String msg ) {
		android.util.Log.i(tag,msg);
	}

	public final static void i( String tag, String msg , Throwable tr ) {
		android.util.Log.i(tag,msg,tr);
	}

	public final static void d( String tag, String msg ) {
		android.util.Log.d(tag,msg);
	}

	public final static void d( String tag, String msg , Throwable tr ) {
		android.util.Log.d(tag,msg,tr);
	}


}
