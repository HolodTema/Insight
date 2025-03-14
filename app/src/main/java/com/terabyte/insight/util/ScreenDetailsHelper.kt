package com.terabyte.insight.util

import android.content.ContentResolver
import android.content.res.Resources
import android.graphics.Point
import android.os.Build
import android.provider.Settings
import android.util.DisplayMetrics
import android.util.Log
import android.view.Display
import android.view.WindowInsets
import android.view.WindowManager
import com.terabyte.insight.model.DeviceDetail
import java.util.Locale
import kotlin.math.round
import kotlin.math.sqrt


object ScreenDetailsHelper {
    private const val MM_IN_INCH = 25.4
    private const val VALUE_NO_INFO = "No information"

    fun getScreenSize(
        resources: Resources,
        windowManager: WindowManager,
        display: Display
    ): DeviceDetail {
        val width = getScreenWidthPixels(resources)
        val height = getScreenHeightPixels(resources, windowManager, display)
        return DeviceDetail(
            "Screen size",
            "${width}px x ${height}px",
            "Screen width and height in pixels"
        )
    }

    fun getScreenDpi(resources: Resources): DeviceDetail {
        val dpi = resources.displayMetrics.densityDpi
        return DeviceDetail(
            "Pixel density",
            "$dpi dpi",
            "Amount of pixels per inc (dots per square inch)"
        )
    }

    fun getScreenDpiX(resources: Resources): DeviceDetail {
        val dpiX = resources.displayMetrics.xdpi
        return DeviceDetail(
            "xdpi",
            "${round(dpiX).toInt()} dpi",
            "Amount of pixels in one inch on X-axis"
        )
    }

    fun getScreenDpiY(resources: Resources): DeviceDetail {
        val dpiY = resources.displayMetrics.xdpi
        return DeviceDetail(
            "ydpi",
            "${round(dpiY).toInt()} dpi",
            "Amount of pixels in one inch on Y-axis"
        )
    }

    fun getScreenSizeMm(resources: Resources, windowManager: WindowManager, display: Display): DeviceDetail {

        val widthPixels = getScreenWidthPixels(resources)
        val heightPixels = getScreenHeightPixels(resources, windowManager, display)

        val widthMm = widthPixels / resources.displayMetrics.xdpi * MM_IN_INCH
        val heightMm = heightPixels / resources.displayMetrics.ydpi * MM_IN_INCH

        return DeviceDetail(
            "Screen size",
            "${round(widthMm).toInt()} mm x ${round(heightMm).toInt()} mm",
            "Screen width and height in millimeters"
        )
    }

    fun getScreenDiagonalInches(resources: Resources, windowManager: WindowManager, display: Display): DeviceDetail {
        val widthPixels = getScreenWidthPixels(resources)
        val heightPixels = getScreenHeightPixels(resources, windowManager, display)

        val widthInches = widthPixels / resources.displayMetrics.xdpi
        val heightInches = heightPixels  / resources.displayMetrics.ydpi
        val diagonal = sqrt(widthInches * widthInches + heightInches * heightInches).toDouble()
        val diagonalStr = String.format(Locale.US, "%.2f", diagonal)
        return DeviceDetail(
            "Screen diagonal",
            "$diagonalStr inches",
            "Screen diagonal in inches."
        )
    }

    fun getBrightness(contentResolver: ContentResolver): DeviceDetail {
        var brightness = VALUE_NO_INFO
        try {
            brightness = Settings.System.getInt(
                contentResolver,
                Settings.System.SCREEN_BRIGHTNESS
            ).toString()
        } catch (e: Settings.SettingNotFoundException) {
            e.printStackTrace()
        }
        return DeviceDetail(
            "Screen brightness",
            brightness,
            "Brightness value from 1 to 255"
        )
    }

    fun getAll(resources: Resources, windowManager: WindowManager, display: Display, resolver: ContentResolver): List<DeviceDetail> {
        return listOf(
            getScreenSize(resources, windowManager, display),
            getScreenDpi(resources),
            getScreenDpiX(resources),
            getScreenDpiY(resources),
            getScreenSizeMm(resources, windowManager, display),
            getScreenDiagonalInches(resources, windowManager, display),
            getBrightness(resolver)
            )
    }

    private fun getNavigationBarHeightPixels(windowManager: WindowManager, display: Display): Int {
        if (Build.VERSION.SDK_INT >= 30) {
            return windowManager
                .currentWindowMetrics
                .windowInsets
                .getInsets(WindowInsets.Type.navigationBars())
                .bottom
        } else {
            val appUsableSize = Point()
            val realScreenSize = Point()
            display.apply {
                getSize(appUsableSize)
                getRealSize(realScreenSize)
            }

            if (appUsableSize.x < realScreenSize.x) {
                return realScreenSize.x - appUsableSize.x
            }
            if (appUsableSize.y < realScreenSize.y) {
                return realScreenSize.y - appUsableSize.y
            }
            return 0
        }
    }

    private fun getStatusBarHeightPixels(resources: Resources): Int {
        val resId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resId > 0) {
            resources.getDimensionPixelSize(resId)
        }
        else {
            0
        }
    }

    private fun getScreenWidthPixels(resources: Resources): Int {
        return resources.displayMetrics.widthPixels
    }

    private fun getScreenHeightPixels(
        resources: Resources,
        windowManager: WindowManager,
        display: Display
    ): Int {
        val appHeight = resources.displayMetrics.heightPixels
        val navigationBarHeight = getNavigationBarHeightPixels(windowManager, display)
        val statusBarHeight = getStatusBarHeightPixels(resources)
        return appHeight + navigationBarHeight + statusBarHeight
    }

}