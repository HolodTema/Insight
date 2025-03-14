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
import com.terabyte.insight.model.DeviceDetail

object SystemDetailsHelper {

    fun getBrand(): DeviceDetail {
        return DeviceDetail(
            "Device brand",
            Build.BRAND,
            "Brand of the device"
        )
    }

    fun getModel(): DeviceDetail {
        return DeviceDetail(
            "Device model",
            Build.MODEL,
            "Model of the device"
        )
    }

    fun getManufacturer(): DeviceDetail {
        return DeviceDetail(
            "Device manufacturer",
            Build.MANUFACTURER,
            "Manufacturer-company of the device"
        )
    }

    @SuppressLint("HardwareIds")
    fun getDeviceId(contentResolver: ContentResolver): DeviceDetail {
        // TODO: understand why it is not safe
        return DeviceDetail(
            "Device ID",
            Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID),
            "Unique 64-bit string of the device. It is supported since Android 8.0 (api26)"
        )
    }

    fun getBuildId(): DeviceDetail {
        return DeviceDetail(
            "Build ID",
            Build.ID,
            "Unique string that displays Android OS build specifications."
        )
    }

    fun getAndroidVersion(): DeviceDetail {
        return DeviceDetail(
            "Android version",
            Build.VERSION.RELEASE,
            "Version of Android OS"
        )
    }

    fun getUserBuildType(): DeviceDetail {
        return DeviceDetail(
            "User build type",
            Build.USER,
            "Android build type, indicates whether build type release or debug. Common values: user, eng, userdebug."
        )
    }

    fun getAndroidType(): DeviceDetail {
        return DeviceDetail(
            "Android build type",
            Build.TYPE,
            "Similar with User build type. Indicates whether build type is debug or release."
        )
    }

    fun getVersionIncremental(): DeviceDetail {
        return DeviceDetail(
            "Build incremental",
            Build.VERSION.INCREMENTAL,
            "String which indicates build updates and changelist. Sometimes it can be in git version control hash."
        )
    }

    fun getVersionCode(): DeviceDetail {
        return DeviceDetail(
            "Android SDK version code",
            Build.VERSION.SDK_INT.toString(),
            "Version code of Android SDK. For example, Android 8.0 has code = 26. Or Android 5.0.1 has code = 21."
        )
    }

    fun getBuildFingerprint(): DeviceDetail {
        return DeviceDetail(
            "Build fingerprint",
            Build.FINGERPRINT,
            "A string that uniquely identifies this build."
        )
    }

    fun getBuildCreationTime(): DeviceDetail {
        val time = DateHelper.millsToDateString(Build.TIME)
        return DeviceDetail(
            "Build creation time",
            time,
            "The time at which the build was produced, given in milliseconds since 01.01.1970"
        )
    }

    fun getBuildTags(): DeviceDetail {
        return DeviceDetail(
            "Build tags",
            Build.TAGS,
            "Comma-separated tags describing the build."
        )
    }

    fun getDeviceIndustrialName(): DeviceDetail {
        return DeviceDetail(
            "Device industrial name",
            Build.DEVICE,
            "Industrial device name for developers."
        )
    }

    fun getProductName(): DeviceDetail {
        return DeviceDetail(
            "Product name",
            Build.PRODUCT,
            "The full name of device (here device is product)"
        )
    }

    fun getCurrentLanguage(resources: Resources): DeviceDetail {
        return DeviceDetail(
            "Language",
            resources.configuration.locales[0].language,
            "Current language which is set in Android."
        )
    }

    fun getCurrentTimeZone(): DeviceDetail {
        return DeviceDetail(
            "Time zone",
            Calendar.getInstance().timeZone.displayName,
            "Current time zone"
        )
    }

    fun getTimeSinceLastBoot(): DeviceDetail {
        return DeviceDetail(
            "Time since last boot",
            DateHelper.millsToSpentTimeString(SystemClock.elapsedRealtime()),
            "Time since last boot of the device in milliseconds."
        )
    }

    fun getGooglePlayServicesVersionCode(packageManager: PackageManager): DeviceDetail {
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

        return DeviceDetail(
            "Google play services version code",
                version,
            "Version code of Google Play Services"
        )
    }

    fun getAll(packageManager: PackageManager, resources: Resources, contentResolver: ContentResolver): List<DeviceDetail> {
        return listOf(
            getBrand(),
            getModel(),
            getManufacturer(),
            getDeviceIndustrialName(),
            getProductName(),
            getDeviceId(contentResolver),
            getBuildId(),
            getAndroidVersion(),
            getVersionCode(),
            getAndroidType(),
            getUserBuildType(),
            getVersionIncremental(),
            getBuildFingerprint(),
            getBuildCreationTime(),
            getBuildTags(),
            getCurrentTimeZone(),
            getCurrentLanguage(resources),
            getTimeSinceLastBoot(),
            getGooglePlayServicesVersionCode(packageManager)
        )
    }

}