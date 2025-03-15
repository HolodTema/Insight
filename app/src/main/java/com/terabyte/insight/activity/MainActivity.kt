package com.terabyte.insight.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.terabyte.insight.R
import com.terabyte.insight.databinding.ActivityMainBinding
import com.terabyte.insight.model.RecyclerMainItem
import com.terabyte.insight.ui.RecyclerMainAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        configureWindowInsets()
        configureRecyclerView()
    }

    private fun configureWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun configureRecyclerView() {
        val items = listOf(
            RecyclerMainItem(
                "System",
                "Information about device and Android build",
                R.drawable.ic_settings,
                SystemDetailsActivity::class.java
            ),
            RecyclerMainItem(
                "Screen and GPU",
                "Screen size, dpi, orientation and graphic processor properties",
                R.drawable.ic_settings,
                ScreenDetailsActivity::class.java
            ),
            RecyclerMainItem(
                "Battery",
                "Battery capacity, status, health and more",
                R.drawable.ic_settings,
                SystemDetailsActivity::class.java
            ),
            RecyclerMainItem(
                "Processor (CPU)",
                "Information about CPU cores, architecture and more",
                R.drawable.ic_settings,
                CpuDetailsActivity::class.java
            ),
            RecyclerMainItem(
                "System usage",
                "Graphs of system usage in percents",
                R.drawable.ic_settings,
                SystemUsageActivity::class.java
            ),

        )
        binding.recyclerMain.layoutManager = LinearLayoutManager(this)
        binding.recyclerMain.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL))
        binding.recyclerMain.adapter = RecyclerMainAdapter(items, layoutInflater, this)
    }
}