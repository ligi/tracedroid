package org.ligi.tracedroid;

import android.content.Context;

import org.ligi.tracedroid.collecting.TraceDroidMetaInfo;
import org.ligi.tracedroid.collecting.UncaughtExceptionSaver;
import org.ligi.tracedroid.logging.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.lang.Thread.UncaughtExceptionHandler;

public class TraceDroid {

    /**
     * registers a UncaughtException handler which saves the stacktrace with
     * some meta-information to the filesystem
     *
     * @param context - the context
     */
    public static void init(Context context) {

        // Gather Info from the Context
        TraceDroidMetaInfo.init(context);

        // get the current handler
        UncaughtExceptionHandler currentHandler = Thread
                .getDefaultUncaughtExceptionHandler();
        if (currentHandler != null) {
            Log.d("current handler class=" + currentHandler.getClass().getName());
        }


        // register our saving handler if not already registered
        if (!(currentHandler instanceof UncaughtExceptionSaver)) {
            // Register default exceptions handler
            UncaughtExceptionSaver exceptionSaver = new UncaughtExceptionSaver(currentHandler);
            Thread.setDefaultUncaughtExceptionHandler(exceptionSaver);
        }
    }

    /**
     * get the dir for the stacktraces and ensure it exists
     *
     * @return
     */
    public static File getEnsuredTraceDir() {
        File trace_files_dir = new File(TraceDroidMetaInfo.getFilesPath());

        // Try to create the files folder if it doesn't exist
        if (!trace_files_dir.exists()) {
            trace_files_dir.mkdirs();
        }

        return trace_files_dir;
    }

    /**
     * get all Stacktrace Files
     *
     * @return all stacktrace files
     */
    public static File[] getStackTraceFiles() {

        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(TraceDroidMetaInfo.getFileSuffix());
            }
        };

        return getEnsuredTraceDir().listFiles(filter);
    }

    public static String getStackTraceText(int limit) {
        Log.d("Searching Exceptions in: " + TraceDroidMetaInfo.getFilesPath());

        String stack_trace_text = "";
        for (File act_file : getStackTraceFiles()) {
            // limit the number of stack traces
            if (limit-- > 0) {
                try {
                    stack_trace_text += "file: " + act_file.toString();
                    stack_trace_text += System.getProperty("line.separator");

                    if (limit-- > 0) {
                        // Read contents of stacktrace file
                        BufferedReader input = new BufferedReader(
                                new FileReader(act_file));
                        try {
                            String line = null;
                            while ((line = input.readLine()) != null) {
                                stack_trace_text += line;
                                stack_trace_text += System
                                        .getProperty("line.separator");
                            }
                        } finally {
                            input.close();
                        }
                    } else
                        stack_trace_text += " discarded by limit";

                } catch (Exception e) {
                    Log.w("problem loading stacktrace", e);
                }
            }
        }

        return stack_trace_text;
    }

    public final static void deleteStacktraceFiles() {
        for (File act_file : getStackTraceFiles()) {
            act_file.delete();
        }
    }

}
