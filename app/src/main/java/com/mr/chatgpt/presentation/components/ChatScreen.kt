package com.mr.chatgpt.presentation.components

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mr.chatgpt.domain.model.Message
import com.mr.chatgpt.presentation.ChatViewModel

@Composable
fun chatScreen(viewModel: ChatViewModel, context: Context) {

    Column(modifier = Modifier.fillMaxSize()) {
        chatMenu()
        chat(viewModel, context)
    }
}
