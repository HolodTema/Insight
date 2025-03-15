package com.terabyte.insight.activity

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.terabyte.insight.R
import com.terabyte.insight.databinding.ActivityCpuDetailsBinding
import com.terabyte.insight.gl.GlRenderer
import com.terabyte.insight.ui.RecyclerDeviceDetailsAdapter
import com.terabyte.insight.util.CpuDetailsHelper
import com.terabyte.insight.util.ScreenDetailsHelper

class CpuDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCpuDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCpuDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configureWindowInsets()
        configureRecyclerView()
        configureButtonBack()
    }

    private fun configureWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun configureRecyclerView() {
        val items = CpuDetailsHelper.getAll()
        val adapter = RecyclerDeviceDetailsAdapter(items, layoutInflater)
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = adapter
    }

    private fun configureButtonBack() {
        binding.imageBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}