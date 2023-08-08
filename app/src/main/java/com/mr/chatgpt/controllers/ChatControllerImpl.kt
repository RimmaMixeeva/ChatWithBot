package com.mr.chatgpt.controllers

import android.content.Context

class ChatControllerImpl: ChatController {
    override fun startVoiceRecording() {
        RecordController.start()
    }

    override fun stopVoiceRecording() {
       RecordController.stop()
    }

    override fun recordingWasStarted(): Boolean {
        return RecordController.recorderIsOn()
    }
}