package com.mr.chatgpt

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import com.mr.chatgpt.components.recordAnimation
import com.mr.chatgpt.components.userPanel
import com.mr.chatgpt.controllers.RecordControllerImpl
import com.mr.chatgpt.manager.FileManager
import com.mr.chatgpt.manager.PathNames


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        setContent {
            userPanel(recorder = recorder)
        }
    }
}
