package com.mr.chatgpt.domain.model

import android.graphics.Bitmap
import android.net.Uri

data class AudioModel (
    val name: String,
    val url: String,
    val durationInMillis: Long,
    val artist: String,
)