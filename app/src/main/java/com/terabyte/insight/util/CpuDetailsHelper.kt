package com.terabyte.insight.util

import android.app.ActivityManager
import android.os.Build
import android.os.Debug
import com.terabyte.insight.TEXT_NO_INFORMATION
import com.terabyte.insight.model.DeviceDetail


object CpuDetailsHelper {

    fun getCpuModel(): DeviceDetail {
        return DeviceDetail(
            "CPU model",
            Build.HARDWARE,
            ""
        )
    }

    fun getCpuArch(): DeviceDetail {
        return DeviceDetail(
            "CPU architecture",
            System.getProperty("os.arch") ?: TEXT_NO_INFORMATION,
            ""
        )
    }

    fun getCpuAbi(): DeviceDetail {
        var value = ""
        Build.SUPPORTED_ABIS.forEach { it ->
            value += "$it\n"
        }
        return DeviceDetail(
            "CPU ABI",
            value,
            "Application binary interfaces (ABI) that are supported by CPU"
        )
    }

    fun getAmountCores(): DeviceDetail {
        return DeviceDetail(
            "Amount of CPU cores",
            Runtime.getRuntime().availableProcessors().toString(),
            ""
        )
    }


    fun getCpuDetailedInfo(): DeviceDetail {
        var value = TEXT_NO_INFORMATION
        try {
            val commands = arrayOf("/system/bin/cat", "/proc/cpuinfo")
            val processBuilder = ProcessBuilder(*commands)
            val process = processBuilder.start()
            val inputStream = process.inputStream
            val bytes = ByteArray(1024)
            value = ""
            while (inputStream.read(bytes) != -1) {
                value += String(bytes)
            }
            inputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
            if (value.isEmpty()) {
                value = TEXT_NO_INFORMATION
            }
        }
        return DeviceDetail(
            "Detailed CPU info",
            value,
            "CPU info from file /proc/cpuinfo"
        )
    }

    fun getAll(): ArrayList<DeviceDetail> {
        return arrayListOf(
            getCpuModel(),
            getCpuArch(),
            getCpuAbi(),
            getAmountCores(),
            getCpuDetailedInfo(),
        )
    }
}