package org.ligi.tracedroid.logging;

/**
 * a drop in replacement and wrapper for the android Log class
 * - extended with a cache of the last log entries ( e.g. for sending a crash report)
 * - shortcuts for the i e w ad d functions with a predefined ( app wide ) TAG
 *
 * @author Marcus -ligi- Bueschleb
 */
public class Log {

    public final static int DEFAULT_LOG_CACHE_SIZE = 42; // in lines
    private static String TAG = "TraceDroid";
    private static String[] log_cache = new String[DEFAULT_LOG_CACHE_SIZE];
    private static int log_cache_pos;

    // shortcuts with a predefined tag
    public static String getTAG() {
        return TAG;
    }

    public static void setTAG(String tag) {
        TAG = tag;
    }

    // shortcuts for logging with predefined TAG
    public final static void e(String msg) {
        e(TAG, msg);
    }

    public final static void e(String msg, Throwable tr) {
        e(TAG, msg, tr);
    }

    public final static void w(String msg) {
        w(TAG, msg);
    }

    public final static void w(String msg, Throwable tr) {
        w(TAG, msg, tr);
    }

    public final static void i(String msg) {
        i(TAG, msg);
    }

    public final static void i(String msg, Throwable tr) {
        i(TAG, msg, tr);
    }


    public final static void d(String msg) {
        d(TAG, msg);
    }

    public final static void d(String msg, Throwable tr) {
        d(TAG, msg, tr);
    }


    // stuff for log caching

    public static void setLogCacheSize(int size) {
        log_cache_pos = 0;
        log_cache = new String[size];
    }

    private synchronized static void doCachedLog(String symbol, String what) {
        log_cache[log_cache_pos] = symbol + ": " + what;
        log_cache_pos = (log_cache_pos + 1) % log_cache.length;
    }

    public synchronized static String getCachedLog() {
        String res = "";
        int pos_in_arr;
        for (int i = 0; i < log_cache.length; i++) {
            pos_in_arr = (log_cache.length - i + log_cache_pos - 1) % log_cache.length;
            if (log_cache[pos_in_arr] != null)
                res += "" + i + " " + log_cache[pos_in_arr] + "\n";
        }
        return res;
    }

    // mappers from this Log to android Util Log

    public final static void e(String tag, String msg) {
        doCachedLog("E", msg);
        android.util.Log.e(tag, msg);
    }

    public final static void e(String tag, String msg, Throwable tr) {
        doCachedLog("E", msg + tr);
        android.util.Log.e(tag, msg, tr);
    }

    public final static void w(String tag, String msg) {
        doCachedLog("W", msg);
        android.util.Log.w(tag, msg);
    }

    public final static void w(String tag, String msg, Throwable tr) {
        doCachedLog("W", msg + tr);
        android.util.Log.w(tag, msg, tr);
    }

    public final static void i(String tag, String msg) {
        doCachedLog("I", msg);
        android.util.Log.i(tag, msg);
    }

    public final static void i(String tag, String msg, Throwable tr) {
        doCachedLog("I", msg + tr);
        android.util.Log.i(tag, msg, tr);
    }

    public final static void d(String tag, String msg) {
        doCachedLog("D", msg);
        android.util.Log.d(tag, msg);
    }

    public final static void d(String tag, String msg, Throwable tr) {
        doCachedLog("D", msg + tr);
        android.util.Log.d(tag, msg, tr);
    }
}
