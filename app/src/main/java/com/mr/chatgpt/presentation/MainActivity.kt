package com.mr.chatgpt.presentation

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter

import com.mr.chatgpt.presentation.components.imageGallery
import com.mr.chatgpt.presentation.components.selectionMenu
import com.mr.chatgpt.presentation.components.userPanel
import com.mr.chatgpt.domain.controllers.RecordControllerImpl
import com.mr.chatgpt.domain.manager.FileManager
import com.mr.chatgpt.domain.manager.Gallery
import com.mr.chatgpt.domain.manager.PathNames
import com.mr.chatgpt.domain.model.VideoModel


class MainActivity : ComponentActivity() {

    lateinit var chatViewModel: ChatViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        chatViewModel = ChatViewModel(this.application)

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

        setContent {
            userPanel(recorder = recorder)
//            selectionMenu(backgroundColor = Color.Gray, textColor = Color.White, wasChosenColor = Color.Blue, context = this, chatViewModel = chatViewModel)
        }

    }
}
