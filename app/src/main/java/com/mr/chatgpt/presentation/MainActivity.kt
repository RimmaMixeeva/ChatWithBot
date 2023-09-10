package com.mr.chatgpt.presentation

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.mr.chatgpt.presentation.components.userPanel
import com.mr.chatgpt.domain.controllers.RecordControllerImpl
import com.mr.chatgpt.domain.manager.FileManager
import com.mr.chatgpt.domain.manager.Gallery
import com.mr.chatgpt.domain.manager.PathNames
import com.mr.chatgpt.presentation.components.chatScreen


class  MainActivity : ComponentActivity() {

    lateinit var chatViewModel: ChatViewModel

    val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        chatViewModel = ViewModelProvider(this)[ChatViewModel::class.java]

        FileManager.createFolder(PathNames.EXTERNAL_APPLICATION_APP)
        FileManager.createFolder(PathNames.EXTERNAL_APPLICATION_RECORDINGS)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.MANAGE_EXTERNAL_STORAGE),
            777
        )

        var recorder = RecordControllerImpl()
        Gallery.fillVideoList(this, chatViewModel)
        Gallery.fillAudioList(this, chatViewModel)

        setContent {
            chatScreen(chatViewModel, context)
            userPanel(recorder = recorder, context = context, viewModel = chatViewModel )
        }

    }
}
