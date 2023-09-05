package com.mr.chatgpt.presentation.components

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.mr.chatgpt.presentation.ChatViewModel
import com.mr.chatgpt.ui.theme.DarkFill
import com.mr.chatgpt.ui.theme.LightGrey
import com.mr.chatgpt.ui.theme.LightYellow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun userPanel(recorder: RecordControllerImpl, context: Context, viewModel: ChatViewModel) {

    val coroutineScope = rememberCoroutineScope()

    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )
    BackHandler(modalSheetState.isVisible) {
        coroutineScope.launch { modalSheetState.hide() }
    }

    var textInput by remember {
        mutableStateOf("")
    }
    var isOn by remember {
        mutableStateOf(false)
    }


    viewModel.galleryIsOpened.observe(context as ComponentActivity) {
        if (viewModel.galleryIsOpened.value == false) {
            coroutineScope.launch {
                if (modalSheetState.isVisible)
                    modalSheetState.hide()
            }
        }
    }

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(600.dp)
            ) {
                selectionMenu(
                    backgroundColor = DarkFill,
                    textColor = LightGrey,
                    textChosenColor = DarkFill,
                    wasChosenColor = LightYellow,
                    context = context,
                    chatViewModel = viewModel
                )
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {

            Box(
                modifier = Modifier
                    .background(LightYellow)
                    .wrapContentSize()
            ) {
                Column() {
                    if(viewModel.chatMessage.video!= null) Text(text = "Было прикреплено видео")
                    if(viewModel.chatMessage.audio!= null) Text(text = "Была прикреплено аудиозапись")
                    if(viewModel.chatMessage.photo!= null) Text(text = "Была прикреплена фотография")
                    
                TextField(
                    value = textInput,
                    onValueChange = { new ->
                        if (!isOn) textInput = new
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = LightYellow,
                        textColor = DarkFill,
                        cursorColor = DarkFill,
                        focusedIndicatorColor = LightYellow,
                        unfocusedIndicatorColor = LightYellow,
                        disabledIndicatorColor = LightYellow
                    ),
                    leadingIcon = {
                        IconButton(onClick = { textInput = "" }) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.delete),
                                contentDescription = "delete"
                            )
                        }
                    },
                    trailingIcon = {
                        if (textInput == "" && !isOn) Row {
                            if (!isOn) {
                                IconButton(onClick = {
                                    coroutineScope.launch {
                                        viewModel.galleryIsOpened.postValue(true)
                                        modalSheetState.animateTo(ModalBottomSheetValue.Expanded)
                                    }
                                }) {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(id = R.drawable.attach_icon),
                                        contentDescription = "attach file"
                                    )
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
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.send),
                                contentDescription = "send"
                            )
                        }
                    },
                    placeholder = {
                        if (isOn) Text("Recording...", color = DarkFill)
                        else Text("Message")
                    },
                    enabled = !isOn
                )
                }
            }
        }
    }

}