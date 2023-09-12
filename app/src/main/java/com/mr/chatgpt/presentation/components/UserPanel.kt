package com.mr.chatgpt.presentation.components

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData

import androidx.lifecycle.lifecycleScope
import com.mr.chatgpt.R
import com.mr.chatgpt.domain.controllers.RecordControllerImpl
import com.mr.chatgpt.domain.manager.Gallery
import com.mr.chatgpt.domain.model.AudioModel
import com.mr.chatgpt.domain.model.Message
import com.mr.chatgpt.domain.model.getEventsFlow
import com.mr.chatgpt.presentation.ChatViewModel
import com.mr.chatgpt.ui.theme.DarkFill
import com.mr.chatgpt.ui.theme.LightGrey
import com.mr.chatgpt.ui.theme.LightYellow
import com.mr.chatgpt.utils.TimeHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

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

    getEventsFlow().asLiveData(Dispatchers.IO).observe(context as LifecycleOwner, Observer {
            event ->
        // Get the Activity's lifecycleScope and launch
        context.lifecycleScope.launch(Dispatchers.Main) {
            // run the code again in IO context
            withContext(Dispatchers.IO) {
                if (event.name == "put") {
                    var message = Message()
                    message.sender = "bot"
                    message.text = event.data.substring(935, 1022)
                    message.time = TimeHelper.getCurrentTimeLong()
                    message.data = Date().time
                    message.showTime = TimeHelper.getCurrentTimeString()
                    viewModel.insertExercise(message)
                }
            }
        }
    }
    )


    var textInput by remember {
        mutableStateOf("")
    }
    var isOn by remember {
        mutableStateOf(false)
    }

    var attachWhat by remember {
        mutableStateOf("")
    }


    viewModel.galleryIsOpened.observe(context as ComponentActivity) {
        if (viewModel.chatMessage.photo != null) attachWhat = "PHOTO"
        else if (viewModel.chatMessage.video != null) attachWhat = "VIDEO"
        else if (viewModel.chatMessage.audio != null) attachWhat = "AUDIO"
        else attachWhat = ""
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
                    Box(
                        modifier = Modifier
                            .shadow(1.dp)
                            .fillMaxWidth()
                    ) {
                    when (attachWhat) {
                        "PHOTO" -> {
                            ShowImage(image = viewModel.chatMessage.photo!!, 70.dp)
                        }
                        "VIDEO" ->  {
                            ShowVideo(video = viewModel.chatMessage.video!!, 80.dp)
                        }
                        "AUDIO" ->  {
                            ShowAudio(audio = viewModel.chatMessage.audio)
                        }
                    }
                    }
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
                            IconButton(onClick = {
                                if (isOn) {
                                    isOn = false
                                    recorder.stopAndDelete()
                                }
                                textInput = ""
                                viewModel.chatMessage = Message()
                                attachWhat = ""
                            }) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.delete),
                                    contentDescription = "delete"
                                )
                            }
                        },
                        trailingIcon = {
                            if (textInput == "" && !isOn && attachWhat == "") Row {

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
                                    viewModel.chatMessage.audio = recorder.getAudio(recorder.currentFile?:"")
                                }
                                    viewModel.chatMessage.text = textInput
                                    viewModel.chatMessage.sender = viewModel.currentSender
                                    viewModel.currentSender =
                                        if (viewModel.currentSender == "me") "me" else "me"
                                    viewModel.chatMessage.time = TimeHelper.getCurrentTimeLong()
                                    viewModel.chatMessage.data = Date().time
                                    viewModel.chatMessage.showTime =
                                        TimeHelper.getCurrentTimeString()
                                    viewModel.insertExercise(viewModel.chatMessage)
                                    textInput = ""
                                    attachWhat = ""
                                    textInput = ""
                                    viewModel.chatMessage = Message()

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



