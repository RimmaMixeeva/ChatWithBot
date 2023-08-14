package com.mr.chatgpt.controllers

import kotlinx.coroutines.flow.Flow

interface RecordController {
    fun getAudioPath(): String
    fun start()
    fun stop()
    fun recorderIsOn(): Boolean
    fun getAmplitude(): Flow<Int>
    fun getAmplitudeDelay(): Int
}