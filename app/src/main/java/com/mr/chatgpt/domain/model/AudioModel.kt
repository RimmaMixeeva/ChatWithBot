package com.mr.chatgpt.domain.model


data class AudioModel (
    val name: String = "",
    val url: String = "",
    val durationInMillis: Long = 0,
    val artist: String = "",
)