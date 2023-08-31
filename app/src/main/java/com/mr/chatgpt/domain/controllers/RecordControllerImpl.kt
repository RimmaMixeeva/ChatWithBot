package com.mr.chatgpt.domain.controllers

import android.media.MediaRecorder
import android.util.Log
import com.mr.chatgpt.domain.manager.PathNames
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.math.log10

class RecordControllerImpl: RecordController {

    var audioRecorder: MediaRecorder? = null

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
        audioRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.VOICE_RECOGNITION)
            setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(getAudioPath())
            prepare()
            start()
        }
    }

    override fun stop() {
        audioRecorder?.let {
            it.stop()
            it.release()
        }
        audioRecorder = null
        Log.d("TEST", "RECORDING STOPPED")
    }

    override fun recorderIsOn(): Boolean = (audioRecorder != null)

    override fun getAmplitude(): Flow<Int> {
        return flow {
            delay(getAmplitudeDelay().toLong())
            var logamplitude = log10((audioRecorder?.maxAmplitude ?: 0.1).toFloat()) *10
            emit(logamplitude.toInt())

        }.flowOn(Dispatchers.Default)
    }
    override fun getAmplitudeDelay(): Int {
        return 30
    }

}