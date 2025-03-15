package com.terabyte.insight.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.terabyte.insight.R
import com.terabyte.insight.databinding.ActivitySystemUsageBinding
import com.terabyte.insight.util.SystemUsageHelper


class SystemUsageActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySystemUsageBinding

    private var listTotalSystemUsage = arrayListOf<DataPoint>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySystemUsageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configureWindowInsets()
        configureButtonBack()
        configureTotalSystemUsageGraph()
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
        SystemUsageHelper.getEndlessTotalSystemUsage(1) { usagePercents ->
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
                    newListTotalSystemUsage.add(DataPoint(it.x-1, it.y))
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
}