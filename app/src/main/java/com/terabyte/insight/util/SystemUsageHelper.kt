package com.terabyte.insight.util

import android.app.ActivityManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Thread.sleep

object SystemUsageHelper {
    const val SYSTEM_USAGE_INCORRECT = -1.0

    const val TOTAL_MEMORY_INCORRECT = -1L
    const val USED_MEMORY_INCORRECT = -1L
    const val MEMORY_USAGE_INCORRECT = -1.0

    fun getEndlessTotalSystemUsage(delayMills: Long, listener: (Double) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            var isRunning = true
            var result = SYSTEM_USAGE_INCORRECT
            while(isRunning) {
                sleep(delayMills)
                try {
                    val process = Runtime.getRuntime()
                        .exec("uptime")
                    val reader = BufferedReader(InputStreamReader(process.inputStream))
                    val line = reader.readLine()
                    val lineSplitArray = line.split(" ")
                    var valueWithComma = ""
                    lineSplitArray.forEachIndexed { index, word ->
                        if (word.contains("average")) {
                            valueWithComma = lineSplitArray[index+1]
                            return@forEachIndexed
                        }
                    }
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


    //This function every delayMills time period invokes listener with memory usage info
    //
    //listener(Long, Long, Double) has 3 args:
    //arg1: Long - total available RAM in kernel in Bytes. Swap file for RAM is included to this value
    //arg2: Long - used RAM in Bytes
    //arg3: Double - percent of usage RAM = total RAM / used RAM * 100%
    //
    //When an error happened, endless listener() invocation is stopped.
    //In the last listener() invocation fields will be with const values
    //like TOTAL_MEMORY_INCORRECT, USED_MEMORY_INCORRECT, MEMORY_USAGE_INCORRECT
    fun getEndlessMemoryUsage(activityManager: ActivityManager, delayMills: Long, listener: (Long, Long, Double) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            var isRunning = true
            var totalMemory = TOTAL_MEMORY_INCORRECT
            var usedMemory = USED_MEMORY_INCORRECT
            var memoryUsage = MEMORY_USAGE_INCORRECT
            while(isRunning) {
                sleep(delayMills)
                try {
                    val memoryInfo = ActivityManager.MemoryInfo()
                    activityManager.getMemoryInfo(memoryInfo)
                    totalMemory = memoryInfo.totalMem
                    usedMemory = totalMemory - memoryInfo.availMem
                    memoryUsage = (usedMemory * 100L / totalMemory).toDouble()
                }
                catch(e: Exception) {
                    e.printStackTrace()
                    isRunning = false
                }
                launch(Dispatchers.Main) {
                    listener(totalMemory, usedMemory, memoryUsage)
                }
            }
        }
    }

}