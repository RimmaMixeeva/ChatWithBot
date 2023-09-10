package com.mr.chatgpt.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
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
import com.mr.chatgpt.presentation.components.audioGallery
import com.mr.chatgpt.presentation.components.chatScreen
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

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
