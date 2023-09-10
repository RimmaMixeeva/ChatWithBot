package com.mr.chatgpt.domain.controllers

import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.util.Log
import com.mr.chatgpt.domain.manager.FileManager
import com.mr.chatgpt.domain.manager.PathNames
import com.mr.chatgpt.domain.model.AudioModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.math.log10

class RecordControllerImpl : RecordController {

    var audioRecorder: MediaRecorder? = null

    var currentFile: String? = null

    override fun getAudioPath(): String {
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("dd_MM_yyyy")
        val currentTime = SimpleDateFormat("HH_mm")

        val formattedDate = dateFormat.format(currentDate)
        val formattedTime = currentTime.format(currentDate)

        return "${PathNames.EXTERNAL_APPLICATION_RECORDINGS}/${formattedDate}_${formattedTime}.wav"
    }

    override fun start() {
        Log.d("TEST", "RECORDING STARTED...")
        currentFile = getAudioPath()
        audioRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.VOICE_RECOGNITION)
            setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(currentFile)
            prepare()
            start()
        }
    }

    fun getAudio(url: String): AudioModel {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(url)
        val durationString = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        val duration = durationString?.toLongOrNull() ?: 0L
        val artist = "<unknown>"
        val name =  FileManager.getFileNameFromUrl(url)
        retriever.release()
        return AudioModel(name, url, duration, artist)
    }

    override fun stop() {
        audioRecorder?.let {
            it.stop()
            it.release()
        }
        audioRecorder = null
    }

    override fun stopAndDelete() {
        audioRecorder?.let {
            it.stop()
            it.release()
        }
        audioRecorder = null

        currentFile?.let { file->
            val file = File(file)
            if(file.exists()){
                file.delete()
            }
        }
        currentFile = null
    }

    override fun recorderIsOn(): Boolean = (audioRecorder != null)

    override fun getAmplitude(): Flow<Int> {
        return flow {
            delay(getAmplitudeDelay().toLong())
            var logamplitude = log10((audioRecorder?.maxAmplitude ?: 0.1).toFloat()) * 10
            emit(logamplitude.toInt())

        }.flowOn(Dispatchers.Default)
    }

    override fun getAmplitudeDelay(): Int {
        return 30
    }

}