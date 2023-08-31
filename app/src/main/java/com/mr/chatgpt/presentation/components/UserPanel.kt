package com.mr.chatgpt.presentation.components

import android.util.Log
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.mr.chatgpt.R
import com.mr.chatgpt.domain.controllers.RecordController
import com.mr.chatgpt.domain.controllers.RecordControllerImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

@Composable
fun userPanel(recorder: RecordControllerImpl) {

    var textInput by remember {
        mutableStateOf("")
    }
    var isOn by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {

        Box(modifier = Modifier.wrapContentSize()) {
            TextField(
                value = textInput,
                onValueChange = { new ->
                    if (!isOn) textInput = new },
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
                    if (textInput == "" && !isOn) Row {
                        if (!isOn) {
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(imageVector = ImageVector.vectorResource(id = R.drawable.attach_icon), contentDescription = "attach file")
                            }
                            IconButton(onClick = {
                                isOn = true
                                recorder.start()
                            }) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.mic),
                                    contentDescription = "mic"
                                )
                            }
                        }
                    } else IconButton(onClick = {
                        if (isOn) {
                            isOn = false
                            recorder.stop()
                        } else {
                            textInput = ""
                        }
                    }) {
                        Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = "send")
                    }
                },
                placeholder = {
                    if (isOn) Text("Recording...", color = Color.Red)
                    else Text("Message")
                },
                enabled = !isOn
            )
        }
    }
}