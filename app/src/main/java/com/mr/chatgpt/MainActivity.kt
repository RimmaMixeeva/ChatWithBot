package com.mr.chatgpt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.material.*
import androidx.compose.material.TextField
import com.mr.chatgpt.components.ChatScreen
import com.mr.chatgpt.components.UserPanel
import com.mr.chatgpt.ui.theme.ChatGPTTheme
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocketListener

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val request = Request.Builder().url("ws://localhost:8010").build()
        val webSocketListener = MyWebSocketListener()
        val webSocket = OkHttpClient().newWebSocket(request, webSocketListener)

        super.onCreate(savedInstanceState)
        setContent {
            TextField(value = "t", onValueChange = {})
            Button(onClick = { /*TODO*/ }) {
                Text("Send")
            }
        }
    }

}

