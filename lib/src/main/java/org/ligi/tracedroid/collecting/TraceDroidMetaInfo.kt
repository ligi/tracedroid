package org.ligi.tracedroid.collecting

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import org.ligi.tracedroid.BuildConfig

data class TraceDroidMetaInfo(
        val androidVersion: String,
        val phoneModel: String,
        val filesPath: String,
        val appVersion: String?,
        val appPackageName: String?,
        val fileSuffix: String,
        val traceDroidVersion: String
)

internal fun createMetaInfo(context: Context): TraceDroidMetaInfo {
    val pi: PackageInfo? = try {
        context.packageManager.getPackageInfo(context.packageName, 0)
    } catch (e: PackageManager.NameNotFoundException) {
        null
    }
    return TraceDroidMetaInfo(
            androidVersion = Build.VERSION.RELEASE,
            phoneModel = Build.MODEL,
            filesPath = context.filesDir.absolutePath,
            appVersion = pi?.versionName ?: "unknown",
            appPackageName = pi?.packageName ?: "unknown",
            fileSuffix = ".tracedroid",
            traceDroidVersion = BuildConfig.TraceDroidVersion
    )
}