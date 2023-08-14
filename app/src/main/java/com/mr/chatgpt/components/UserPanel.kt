package com.mr.chatgpt.components

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
import com.mr.chatgpt.controllers.RecordController
import com.mr.chatgpt.controllers.RecordControllerImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

@Composable
fun userPanel(recorder: RecordControllerImpl) {

    val duration = 1000
    val maxScale = 7

    var lw by remember {
        mutableStateOf(0)
    }
    var lh by remember {
        mutableStateOf(0)
    }
    var mw by remember {
        mutableStateOf(0)
    }
    var mh by remember {
        mutableStateOf(0)
    }

    var iconScale = 1
    val ms by recorder.getAmplitude().collectAsState(initial = 3)

    val movingIconScale by animateIntAsState(targetValue = ms, animationSpec = tween(durationMillis = recorder.getAmplitudeDelay()))
    val largestWidth by animateIntAsState(targetValue = lw, animationSpec = tween(durationMillis = duration))
    val largestHeight by animateIntAsState(targetValue = lh, animationSpec = tween(durationMillis = duration))
    val middleWidth by animateIntAsState(targetValue = mw, animationSpec = tween(durationMillis = duration))
    val middleHeight by animateIntAsState(targetValue = mh, animationSpec = tween(durationMillis = duration))

    LaunchedEffect(true) {
        while (true) {
            withContext(Dispatchers.Default) {
                delay(duration.toLong())
                if (largestWidth == 5) {
                    lw = 0
                    lh = 5
                    mw = 10
                    mh = 0
                } else {
                    lw = 5
                    lh = 0
                    mw = 0
                    mh = 5
                }
            }
        }
    }


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
        if (isOn) {
            Box(contentAlignment = Alignment.BottomEnd, modifier = Modifier.zIndex(1f).fillMaxSize() .graphicsLayer(clip = false)) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.requiredSize(100.dp).background(
                    Color.Red
                )
                    .graphicsLayer(clip = false)) {

                    val cyanColor = Color.Cyan
                    val lightTransparentCyanColor = cyanColor.copy(alpha = 0.2f)
                    val heavyTtransparentCyanColor = cyanColor.copy(alpha = 0.23f)

                    Box(
                        modifier = Modifier
                            .clip(
                                CircleShape
                            )
                            .background(lightTransparentCyanColor)
                            .width((iconScale * 28 + (((if (movingIconScale > 21) movingIconScale else 21) - 21) * maxScale / 25) * 10 + largestWidth).dp)
                            .height((iconScale * 28 + (((if (movingIconScale > 21) movingIconScale else 21) - 21) * maxScale / 25) * 10 + largestHeight).dp)
                            .graphicsLayer(clip = false)
                    )
                    Box(
                        modifier = Modifier
                            .clip(
                                CircleShape
                            )
                            .background(heavyTtransparentCyanColor)
                            .width((iconScale * 27 + (((if (movingIconScale > 21) movingIconScale else 21) - 21) * maxScale / 25) * 10 + middleWidth).dp)
                            .height((iconScale * 27 + (((if (movingIconScale > 21) movingIconScale else 21) - 21) * maxScale / 25) * 10 + middleHeight).dp)
                    )
                    IconButton(onClick = {
                        recorder.stop()
                        isOn = recorder.recorderIsOn()
                    }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = "audio",
                            tint = Color.White,
                            modifier = Modifier
                                .size((iconScale * 25).dp)
                                .clip(
                                    CircleShape
                                )
                                .background(cyanColor)

                        )
                    }
                }
            }
        }
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
                    if (textInput == "") Row {
                        if (!isOn) {
                            IconButton(onClick = {
                                isOn = true
                                recorder.start()
                            }) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.mic),
                                    contentDescription = "add"
                                )
                            }
                        }
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Filled.Add, contentDescription = "add")
                        }

                    } else IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = "send")
                    }
                },
                placeholder = {
                    if (isOn) Text("Recording...")
                    else Text("Message")
                },
                enabled = !isOn
            )
        }
    }
}