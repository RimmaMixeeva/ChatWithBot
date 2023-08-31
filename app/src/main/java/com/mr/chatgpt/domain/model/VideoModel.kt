package com.mr.chatgpt.domain.model

import android.graphics.Bitmap
import android.net.Uri

data class VideoModel (
    val url: String,
    val bitmap: Bitmap,
    val durationInMillis: Long
)