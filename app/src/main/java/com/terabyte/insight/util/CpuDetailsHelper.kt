package com.terabyte.insight.util

import android.os.Build
import android.util.Log
import com.terabyte.insight.TEXT_NO_INFORMATION
import com.terabyte.insight.model.DeviceDetail
import com.terabyte.insight.util.CpuDetailsHelper.getAmountCores
import com.terabyte.insight.util.CpuDetailsHelper.getCpuAbi
import com.terabyte.insight.util.CpuDetailsHelper.getCpuArch
import com.terabyte.insight.util.CpuDetailsHelper.getCpuLoadPercents
import com.terabyte.insight.util.CpuDetailsHelper.getCpuModel
import java.io.BufferedReader
import java.io.InputStreamReader


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

    fun getCpuLoadPercents(): DeviceDetail {

        var line = TEXT_NO_INFORMATION
        try {
            val process = Runtime.getRuntime()
                .exec("top -m 10")
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            while (!line.contains("%CPU")) {
                line = reader.readLine()
            }
            Log.d("mydebug", line)
            line = reader.readLine()
            Log.d("mydebug", line)
//            line = reader.readLine().split(" ")[8]
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
        return DeviceDetail(
            "Cpu usage",
            line,
            "Processor usage in percents. Not supported after Android 11."
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
            getCpuLoadPercents(),
            getCpuDetailedInfo()
        )
    }
}