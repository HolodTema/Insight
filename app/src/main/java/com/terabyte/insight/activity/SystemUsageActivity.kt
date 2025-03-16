package com.terabyte.insight.activity

import android.app.ActivityManager
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.terabyte.insight.LOG_DEBUG
import com.terabyte.insight.R
import com.terabyte.insight.databinding.ActivitySystemUsageBinding
import com.terabyte.insight.util.SystemUsageHelper
import java.util.Locale
import kotlin.math.round


class SystemUsageActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySystemUsageBinding

    private var listTotalSystemUsage = arrayListOf<DataPoint>()
    private var listMemoryUsage = arrayListOf<DataPoint>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySystemUsageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configureWindowInsets()
        configureButtonBack()
        configureTotalSystemUsageGraph()
        configureMemoryUsageGraph()
    }

    private fun configureWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun configureButtonBack() {
        binding.imageBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun configureTotalSystemUsageGraph() {
        binding.graphTotalSystemUsage.title = "total system usage"

        binding.graphTotalSystemUsage.viewport.isXAxisBoundsManual = true
        binding.graphTotalSystemUsage.viewport.isYAxisBoundsManual = true
        binding.graphTotalSystemUsage.viewport.setMinX(0.0)
        binding.graphTotalSystemUsage.viewport.setMaxX(15.0)
        binding.graphTotalSystemUsage.viewport.setMinY(0.0)
        binding.graphTotalSystemUsage.viewport.setMaxY(100.0)
        SystemUsageHelper.getEndlessTotalSystemUsage(500) { usagePercents ->
            if (usagePercents == SystemUsageHelper.SYSTEM_USAGE_INCORRECT) {
                binding.graphTotalSystemUsage.title = "total system usage. No information"
                return@getEndlessTotalSystemUsage
            }
            binding.graphTotalSystemUsage.title = "total system usage = $usagePercents%"

            listTotalSystemUsage.add(DataPoint(listTotalSystemUsage.size.toDouble(), usagePercents))
            if (listTotalSystemUsage.size > 15) {
                val newListTotalSystemUsage = arrayListOf<DataPoint>()
                listTotalSystemUsage.removeAt(0)
                listTotalSystemUsage.forEach {
                    newListTotalSystemUsage.add(DataPoint(it.x - 1, it.y))
                }
                listTotalSystemUsage = newListTotalSystemUsage
            }

            val series = LineGraphSeries(
                listTotalSystemUsage.toTypedArray()
            )
            series.color = Color.GREEN
            series.thickness = 6
            binding.graphTotalSystemUsage.removeAllSeries()
            binding.graphTotalSystemUsage.addSeries(series)
        }
    }

    private fun configureMemoryUsageGraph() {
        binding.textTotalMemory.text = "Total RAM: "
        binding.textUsedMemory.text = "Used RAM: "

        binding.graphMemoryUsage.title = "RAM usage"

        binding.graphMemoryUsage.viewport.isXAxisBoundsManual = true
        binding.graphMemoryUsage.viewport.isYAxisBoundsManual = true
        binding.graphMemoryUsage.viewport.setMinX(0.0)
        binding.graphMemoryUsage.viewport.setMaxX(15.0)
        binding.graphMemoryUsage.viewport.setMinY(0.0)
        binding.graphMemoryUsage.viewport.setMaxY(100.0)

        val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager

        SystemUsageHelper.getEndlessMemoryUsage(
            activityManager,
            500,
        ) { totalMemoryBytes, usedMemoryBytes, usagePercents ->
            Log.d(LOG_DEBUG, "$totalMemoryBytes  $usedMemoryBytes  $usagePercents")

            if (usagePercents == SystemUsageHelper.MEMORY_USAGE_INCORRECT || totalMemoryBytes == SystemUsageHelper.TOTAL_MEMORY_INCORRECT || usedMemoryBytes == SystemUsageHelper.USED_MEMORY_INCORRECT) {
                binding.graphTotalSystemUsage.title = "Memory usage. No information"
                return@getEndlessMemoryUsage
            }

            binding.graphMemoryUsage.title = "Memory usage = $usagePercents%"

            listMemoryUsage.add(DataPoint(listMemoryUsage.size.toDouble(), usagePercents))
            if (listMemoryUsage.size > 15) {
                val newListMemoryUsage = arrayListOf<DataPoint>()
                listMemoryUsage.removeAt(0)
                listMemoryUsage.forEach {
                    newListMemoryUsage.add(DataPoint(it.x - 1, it.y))
                }
                listMemoryUsage = newListMemoryUsage
            }

            val series = LineGraphSeries(
                listMemoryUsage.toTypedArray()
            )
            series.color = Color.BLUE
            series.thickness = 6
            binding.graphMemoryUsage.removeAllSeries()
            binding.graphMemoryUsage.addSeries(series)

            val totalMemoryMegabytes = String.format(Locale.US, "%.2f", totalMemoryBytes / 1024.0 / 1024.0)
            val totalMemoryGigabytes = String.format(Locale.US, "%.2f", totalMemoryBytes / 1024.0 / 1024.0 / 1024.0)
            binding.textTotalMemory.text = "Total RAM: ${totalMemoryBytes}B = ${totalMemoryMegabytes}MB = ${totalMemoryGigabytes}GB"

            val usedMemoryMegabytes = String.format(Locale.US, "%.2f", usedMemoryBytes / 1024.0 / 1024.0)
            val usedMemoryGigabytes = String.format(Locale.US, "%.2f", usedMemoryBytes / 1024.0 / 1024.0 / 1024.0)
            binding.textUsedMemory.text = "Used RAM: ${usedMemoryBytes}B = ${usedMemoryMegabytes}MB = ${usedMemoryGigabytes}GB"

        }
    }
}