package com.mr.chatgpt.domain.model

import android.graphics.Bitmap
import com.mr.chatgpt.data.DBMessage
import java.io.ByteArrayOutputStream

class Message (
    var id: Int = 0,
    var text: String = "",
    var sender: String = "",
    var showTime: String = "",
    var time: Long = 0,
    var data: Long = 0,
    var audio: AudioModel? = null,
    var video: VideoModel? = null,
    var photo: String? = null
    ) {
    fun toDBMessage(): DBMessage {
        val stream = ByteArrayOutputStream()
        video?.bitmap?.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray: ByteArray = stream.toByteArray()
        return DBMessage(id, text, sender, time, data, photo?:"", audio?.name?:"", audio?.url?:"", audio?.durationInMillis?:0, audio?.artist?:"", video?.url?:"", byteArray, video?.durationInMillis?:0)
    }
}