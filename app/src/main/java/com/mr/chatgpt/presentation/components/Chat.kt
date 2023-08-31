package com.mr.chatgpt.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mr.chatgpt.Message

@Composable
fun chat (messages: ArrayList<Message>) {
    LazyColumn(contentPadding = PaddingValues(20.dp), modifier = Modifier.fillMaxHeight(0.9f) ) {
       items(messages) {message ->
           chatMessage(item = message)
       }
    }
}

@Composable
fun chatMessage(item: Message) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp), contentAlignment = if (item.sender == "me") Alignment.CenterEnd else Alignment.CenterStart) {
        Column(modifier = Modifier
            .background(
                if (item.sender == "me") Color.White else Color.Cyan,
                RoundedCornerShape(12.dp)
            )
            .wrapContentSize()) {
            Text(text = item.text, fontSize = 20.sp, modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp))
            Text(text = item.time, fontSize = 10.sp, modifier = Modifier.padding(bottom = 8.dp, start = 8.dp, end = 8.dp), textAlign = TextAlign.End)
        }
    }
}

@Preview
@Composable
fun test() {
    var list = arrayListOf<Message>(Message("hello","me","12:23:30"), Message("bye","bot","12:34:23"),
    Message("hello","me","12:23:23"))
    chat(list);
}