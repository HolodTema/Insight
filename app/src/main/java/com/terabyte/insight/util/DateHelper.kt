package com.terabyte.insight.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


object DateHelper {

    fun millsToDateString(mills: Long, dateSeparator: String = ".", timeSeparator: String = ":"): String {
        val date = Date(mills)
        val sdf = SimpleDateFormat("dd${dateSeparator}MM${dateSeparator}yyyy HH${timeSeparator}mm", Locale.US)
        return sdf.format(date)
    }

    fun millsToSpentTimeString(mills: Long): String {
        val days = mills / 86400000L
        val hours = (mills % 86400000L) / 3600000L
        val minutes = ((mills % 86400000L) % 3600000L) / 60000
        val seconds = (((mills % 86400000L) % 3600000L) % 60000) / 1000L
        return "${days}days ${hours}hours ${minutes}min ${seconds}s"
    }
}