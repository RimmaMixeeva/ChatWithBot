package com.mr.chatgpt.presentation.components

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.mr.chatgpt.presentation.ChatViewModel

@Composable
fun chatScreen(viewModel: ChatViewModel, activity: Activity) {

    Column(modifier = Modifier.fillMaxSize()) {
        chatMenu(activity)
        chat(viewModel, activity)
    }
}
