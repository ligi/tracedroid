package org.ligi.tracedroid.collecting;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

public class TraceDroidMetaInfo {

    private static String app_version = "unknown";
    private static String app_packagename = "unknown";
    private static String android_version = "unknown";
    private static String phone_model = "unknown";
    private static String files_path = "unknown";
    private static String extra = "";

    public static void init(Context context) {
        phone_model = android.os.Build.MODEL;
        android_version = android.os.Build.VERSION.RELEASE;

        files_path = context.getFilesDir().getAbsolutePath();

        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            app_version = pi.versionName;
            app_packagename = pi.packageName;
        } catch (NameNotFoundException e) {
            extra += "No Package found to gather TraceDroidMetaInfo";
        }
    }

    public static String getAppVersion() {
        return app_version;
    }

    public static String getAndroidVersion() {
        return android_version;
    }

    public static String getPhoneModel() {
        return phone_model;
    }

    public static String getFilesPath() {
        return files_path;
    }

    public static String getFileSuffix() {
        return ".tracedroid";
    }

    public static String getAppPackageName() {
        return app_packagename;
    }

    public static String getExtra() {
        return extra;
    }

    public static String getTraceDroidVersion() {
        return "1.1";
        // TODO replace a thing like below - was only showing 0.0 on initial tests
        //return TraceDroidMetaInfo.class.getPackage().getImplementationVersion();
    }
}
