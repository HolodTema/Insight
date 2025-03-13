package com.terabyte.insight.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.terabyte.insight.R
import com.terabyte.insight.databinding.ActivitySystemDetailsBinding
import com.terabyte.insight.ui.RecyclerSystemDetailsAdapter
import com.terabyte.insight.util.SystemDetailsHelper

class SystemDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySystemDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySystemDetailsBinding.inflate(layoutInflater)
        enableEdgeToEdge()
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
        val items = SystemDetailsHelper.getAll(packageManager, resources, contentResolver)
        binding.recyclerSystemDetails.layoutManager = LinearLayoutManager(this)
        binding.recyclerSystemDetails.adapter = RecyclerSystemDetailsAdapter(items, layoutInflater)

    }

    private fun configureButtonBack() {
        binding.imageSystemDetailsBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}