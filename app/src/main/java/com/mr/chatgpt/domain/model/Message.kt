package com.mr.chatgpt.domain.model

class Message (
    val text: String = "",
    val sender: String = "",
    val time: String = "",
    var audio: AudioModel? = null,
    var video: VideoModel? = null,
    var photo: String? = null
    )