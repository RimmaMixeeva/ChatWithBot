package com.mr.chatgpt.domain.model

import android.graphics.Bitmap

data class VideoModel (
    val url: String = "",
    val bitmap: Bitmap? = null,
    val durationInMillis: Long = 0
)