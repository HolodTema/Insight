package com.terabyte.insight.util

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.content.res.Resources
import android.icu.util.Calendar
import android.os.Build
import android.os.SystemClock
import android.provider.Settings
import androidx.core.content.pm.PackageInfoCompat
import com.google.android.gms.common.GoogleApiAvailability
import com.terabyte.insight.model.SystemDetail

object SystemDetailsHelper {

    fun getBrand(): SystemDetail {
        return SystemDetail(
            "Device brand",
            Build.BRAND,
            "Brand of the device"
        )
    }

    fun getModel(): SystemDetail {
        return SystemDetail(
            "Device model",
            Build.MODEL,
            "Model of the device"
        )
    }

    fun getManufacturer(): SystemDetail {
        return SystemDetail(
            "Device manufacturer",
            Build.MANUFACTURER,
            "Manufacturer-company of the device"
        )
    }

    @SuppressLint("HardwareIds")
    fun getDeviceId(contentResolver: ContentResolver): SystemDetail {
        // TODO: understand why it is not safe
        return SystemDetail(
            "Device ID",
            Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID),
            "Unique 64-bit string of the device. It is supported since Android 8.0 (api26)"
        )
    }

    fun getBuildId(): SystemDetail {
        return SystemDetail(
            "Build ID",
            Build.ID,
            "Unique string that displays Android OS build specifications."
        )
    }

    fun getAndroidVersion(): SystemDetail {
        return SystemDetail(
            "Android version",
            Build.VERSION.RELEASE,
            "Version of Android OS"
        )
    }

    fun getUserBuildType(): SystemDetail {
        return SystemDetail(
            "User build type",
            Build.USER,
            "Android build type, indicates whether build type release or debug. Common values: user, eng, userdebug."
        )
    }

    fun getAndroidType(): SystemDetail {
        return SystemDetail(
            "Android build type",
            Build.TYPE,
            "Similar with User build type. Indicates whether build type is debug or release."
        )
    }

    fun getVersionIncremental(): SystemDetail {
        return SystemDetail(
            "Build incremental",
            Build.VERSION.INCREMENTAL,
            "String which indicates build updates and changelist. Sometimes it can be in git version control hash."
        )
    }

    fun getVersionCode(): SystemDetail {
        return SystemDetail(
            "Android SDK version code",
            Build.VERSION.SDK_INT.toString(),
            "Version code of Android SDK. For example, Android 8.0 has code = 26. Or Android 5.0.1 has code = 21."
        )
    }

    fun getBuildFingerprint(): SystemDetail {
        return SystemDetail(
            "Build fingerprint",
            Build.FINGERPRINT,
            "A string that uniquely identifies this build."
        )
    }

    fun getBuildCreationTime(): SystemDetail {
        return SystemDetail(
            "Build creation time",
            Build.TIME.toString(),
            "The time at which the build was produced, given in milliseconds since 01.01.1970"
        )
    }

    fun getBuildTags(): SystemDetail {
        return SystemDetail(
            "Build tags",
            Build.TAGS,
            "Comma-separated tags describing the build."
        )
    }

    fun getDeviceIndustrialName(): SystemDetail {
        return SystemDetail(
            "Device industrial name",
            Build.DEVICE,
            "Industrial device name for developers."
        )
    }

    fun getProductName(): SystemDetail {
        return SystemDetail(
            "Product name",
            Build.PRODUCT,
            "The full name of device (here device is product)"
        )
    }

    fun getCurrentLanguage(resources: Resources): SystemDetail {
        return SystemDetail(
            "Language",
            resources.configuration.locales[0].language,
            "Current language which is set in Android."
        )
    }

    fun getCurrentTimeZone(): SystemDetail {
        return SystemDetail(
            "Time zone",
            Calendar.getInstance().timeZone.displayName,
            "Current time zone"
        )
    }

    fun getTimeSinceLastBoot(): SystemDetail {
        return SystemDetail(
            "Time since last boot",
            SystemClock.elapsedRealtime().toString(),
            "Time since last boot of the device in milliseconds."
        )
    }

    fun getGooglePlayServicesVersionCode(packageManager: PackageManager): SystemDetail {
        val version = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            PackageInfoCompat.getLongVersionCode(
                packageManager.getPackageInfo(
                    GoogleApiAvailability.GOOGLE_PLAY_SERVICES_PACKAGE,
                    0)
            ).toString()
        }
        else {
            packageManager.getPackageInfo(GoogleApiAvailability.GOOGLE_PLAY_SERVICES_PACKAGE, 0).versionCode.toString()
        }

        return SystemDetail(
            "Google play services version code",
                version,
            "Version code of Google Play Services"
        )
    }

}