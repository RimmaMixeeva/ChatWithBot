package com.mr.chatgpt.presentation.components

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mr.chatgpt.domain.model.Message
import com.mr.chatgpt.presentation.ChatViewModel
import com.mr.chatgpt.ui.theme.DarkFill
import com.mr.chatgpt.ui.theme.LightGrey
import com.mr.chatgpt.ui.theme.LightYellow
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.fillMaxWidth


@Composable
fun chat(viewModel: ChatViewModel, context: Context) {

    var chosenMessanges = remember { mutableStateListOf<Message>() }

    viewModel.allMessages.observe(context as ComponentActivity) { data ->
        chosenMessanges.clear()
        if ( data != null) {
            for (each: Message in  data) {
                chosenMessanges.add(each)
            }

    }
    }

    LazyColumn(
        contentPadding = PaddingValues(20.dp), modifier = Modifier
            .fillMaxHeight(0.94f)
            .fillMaxWidth()
            .background(
                DarkFill
            )
    ) {
        items(chosenMessanges.reversed()) { message ->
            chatMessage(item = message)
        }
    }
}

@Composable
fun chatMessage(item: Message) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        contentAlignment = if (item.sender == "me") Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Column(
            modifier = Modifier
                .background(
                    if (item.sender == "me") LightYellow else LightGrey,
                    RoundedCornerShape(12.dp)
                )
                .wrapContentSize()
        ) {

            Text(
                text = item.text,
                fontSize = 20.sp,
                modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp)
            )

            if (item.audio != null) {
                ShowAudio(audio = item.audio)

            }
            if (item.photo != null) {
                ShowImage(image = item.photo!!, size = 70.dp)

            }
            if (item.video != null) {
                ShowVideo(video = item.video!!, size = 70.dp)

            }
            Text(
                text = item.showTime,
                fontSize = 10.sp,
                modifier = Modifier.padding(bottom = 8.dp, start = 8.dp, end = 8.dp),
                textAlign = TextAlign.End
            )
        }
    }
}