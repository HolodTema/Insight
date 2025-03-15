package com.terabyte.insight.util

import android.os.Build
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

    fun getAmountCores(): DeviceDetail {
        return DeviceDetail(
            "Amount of CPU cores",
            Runtime.getRuntime().availableProcessors().toString(),
            ""
        )
    }

    fun getAll(): ArrayList<DeviceDetail> {
        return arrayListOf(
            getCpuModel(),
            getCpuArch(),
            getAmountCores()
        )
    }
}