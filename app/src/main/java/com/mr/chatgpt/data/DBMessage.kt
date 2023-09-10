package com.mr.chatgpt.data

import android.graphics.BitmapFactory
import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mr.chatgpt.domain.model.AudioModel
import com.mr.chatgpt.domain.model.Message
import com.mr.chatgpt.domain.model.VideoModel
import com.mr.chatgpt.utils.TimeHelper


@Entity(
    tableName = "MessageTable",
)
data class DBMessage(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "sender") val sender: String,
    @ColumnInfo(name = "time") val time: Long,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "audio_name") val audioName: String,
    @ColumnInfo(name = "audio_url") val audioUrl: String,
    @ColumnInfo(name = "audio_duration") val audioDuration: Long,
    @ColumnInfo(name = "audio_artist") val audioArtist: String,
    @ColumnInfo(name = "video_url") val videoUrl: String,
    @ColumnInfo(name = "video_bitmap") val videoBitmap: ByteArray,
    @ColumnInfo(name = "video_duration") val videoDuration: Long
) {
    fun toMessage(): Message {
        
        Log.d("TEST", TimeHelper.convertMillisecondsToTime(time).toString())
        return Message(
            id = id,
            text = text,
            sender = sender,
            showTime = TimeHelper.convertMillisecondsToTime(time),
            time = time,
            audio = AudioModel(audioName, audioUrl, audioDuration, audioArtist),
            video = VideoModel(
                videoUrl,
                BitmapFactory.decodeByteArray(videoBitmap, 0, videoBitmap.size),
                videoDuration
            ),
            photo = imageUrl
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DBMessage

        if (id != other.id) return false
        if (text != other.text) return false
        if (sender != other.sender) return false
        if (time != other.time) return false
        if (imageUrl != other.imageUrl) return false
        if (audioName != other.audioName) return false
        if (audioUrl != other.audioUrl) return false
        if (audioDuration != other.audioDuration) return false
        if (audioArtist != other.audioArtist) return false
        if (videoUrl != other.videoUrl) return false
        if (!videoBitmap.contentEquals(other.videoBitmap)) return false
        if (videoDuration != other.videoDuration) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + text.hashCode()
        result = 31 * result + sender.hashCode()
        result = 31 * result + time.hashCode()
        result = 31 * result + imageUrl.hashCode()
        result = 31 * result + audioName.hashCode()
        result = 31 * result + audioUrl.hashCode()
        result = 31 * result + audioDuration.hashCode()
        result = 31 * result + audioArtist.hashCode()
        result = 31 * result + videoUrl.hashCode()
        result = 31 * result + videoBitmap.contentHashCode()
        result = 31 * result + videoDuration.hashCode()
        return result
    }
}