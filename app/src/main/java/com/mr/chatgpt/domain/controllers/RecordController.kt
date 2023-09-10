package com.mr.chatgpt.domain.controllers

import kotlinx.coroutines.flow.Flow

interface RecordController {
    fun getAudioPath(): String
    fun start()
    fun stop()

    fun stopAndDelete()
    fun recorderIsOn(): Boolean
    fun getAmplitude(): Flow<Int>
    fun getAmplitudeDelay(): Int
}