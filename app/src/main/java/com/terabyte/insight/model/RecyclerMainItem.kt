package com.terabyte.insight.model

import android.app.Activity
import androidx.annotation.DrawableRes

data class RecyclerMainItem(
    val title: String,
    val description: String,
    @DrawableRes val drawableId: Int,
    val activityToStart: Class<out Activity>
)