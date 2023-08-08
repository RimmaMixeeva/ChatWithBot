package com.mr.chatgpt.controllers

import android.content.Context

interface ChatController {
    fun startVoiceRecording()
    fun stopVoiceRecording()
    fun recordingWasStarted(): Boolean
}