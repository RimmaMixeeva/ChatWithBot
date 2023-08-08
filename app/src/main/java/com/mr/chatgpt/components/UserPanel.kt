package com.mr.chatgpt.components

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mr.chatgpt.controllers.ChatController
import com.mr.chatgpt.controllers.ChatControllerImpl
import com.mr.chatgpt.controllers.RecordController
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun userPanel(controller: ChatController) {

    var size = MutableStateFlow(RecordController.amplitude)

    var textInput by remember {
        mutableStateOf("")
    }
    TextField(
        value = textInput,
        onValueChange = { new -> textInput = new },
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            textColor = Color.Black,
            cursorColor = Color.Blue,
            focusedIndicatorColor = Color.White,
            unfocusedIndicatorColor = Color.White,
            disabledIndicatorColor = Color.White
        ),
        leadingIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.List, contentDescription = "other")
            }
        },
        trailingIcon = {
            if (textInput == "") Row {
                IconButton(onClick = {
                    if (controller.recordingWasStarted()) controller.startVoiceRecording()
                    else controller.stopVoiceRecording()
                }) {
                    Icon(imageVector = Icons.Filled.Face, contentDescription = "record voice", modifier = Modifier.size(size.value.dp))
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "add")
                }
            } else IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = "send")
            }
        },
        placeholder = {
            Text("Message")
        }
    )
}