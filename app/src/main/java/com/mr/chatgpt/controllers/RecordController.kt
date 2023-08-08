package com.mr.chatgpt.controllers

import android.content.Context
import android.media.MediaRecorder
import android.os.Environment
import android.util.Log
import com.mr.chatgpt.manager.PathNames
import java.io.File
import java.util.concurrent.Flow

object RecordController {

    var audioRecorder: MediaRecorder? = null;

    var amplitude = audioRecorder?.maxAmplitude ?: 0

    fun getAudioPath(): String {
        return "${PathNames.EXTERNAL_APPLICATION_RECORDINGS}/${System.currentTimeMillis()}.wav"
    }

    fun start() {
        Log.d("TEST", "RECORDING STARTED...")
        audioRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(getAudioPath())
            prepare()
            start()
        }
    }

    fun stop() {
        audioRecorder?.let {
            it.stop()
            it.release()
        }
        audioRecorder = null
        Log.d("TEST", "RECORDING STOPPED")
    }

    fun recorderIsOn(): Boolean = (audioRecorder == null)

}