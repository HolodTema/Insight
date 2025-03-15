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
import com.terabyte.insight.databinding.ActivityScreenDetailsBinding
import com.terabyte.insight.gl.GlRenderer
import com.terabyte.insight.ui.RecyclerDeviceDetailsAdapter
import com.terabyte.insight.util.ScreenDetailsHelper
import com.terabyte.insight.util.SystemDetailsHelper

class ScreenDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScreenDetailsBinding

    private var isOpenGlInfoGot = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScreenDetailsBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        configureWindowInsets()
        configureRecyclerView()
        configureButtonBack()


    }

    override fun onResume() {
        super.onResume()
        binding.glSurface.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.glSurface.onPause()
    }

    private fun configureWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun configureRecyclerView() {
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val items = ScreenDetailsHelper.getAll(resources, windowManager, display, contentResolver, activityManager)

        val adapter = RecyclerDeviceDetailsAdapter(items, layoutInflater)
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = adapter

        val glRenderer = GlRenderer { deviceDetails ->
            if (!isOpenGlInfoGot) {
                items.addAll(deviceDetails)
                adapter.notifyDataSetChanged()
                isOpenGlInfoGot = true
            }
        }

        binding.glSurface.setEGLContextClientVersion(2);
        binding.glSurface.setRenderer(glRenderer)
    }

    private fun configureButtonBack() {
        binding.imageBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}