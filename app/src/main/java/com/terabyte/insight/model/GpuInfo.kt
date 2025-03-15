package com.terabyte.insight.model;

data class GpuInfo(
    val vendor: String,
    val glVersion: String,
    val glExtensions: String,
    val glRenderer: String
)
