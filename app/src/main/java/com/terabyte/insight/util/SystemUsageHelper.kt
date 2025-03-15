package com.terabyte.insight.util

import com.terabyte.insight.TEXT_NO_INFORMATION
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Thread.sleep

object SystemUsageHelper {
    const val SYSTEM_USAGE_INCORRECT = -1.0

    fun getEndlessTotalSystemUsage(delaySeconds: Int, listener: (Double) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            var isRunning = true
            var result = SYSTEM_USAGE_INCORRECT
            while(isRunning) {
                sleep(1000L*delaySeconds)
                try {
                    val process = Runtime.getRuntime()
                        .exec("uptime")
                    val reader = BufferedReader(InputStreamReader(process.inputStream))
                    val valueWithComma = reader.readLine().split(" ")[13]
                    result = valueWithComma.substringBefore(',').toDouble()
                }
                catch (e: Exception) {
                    e.printStackTrace()
                    isRunning = false
                }
                launch(Dispatchers.Main) {
                    listener(result)
                }
            }
        }
    }

}