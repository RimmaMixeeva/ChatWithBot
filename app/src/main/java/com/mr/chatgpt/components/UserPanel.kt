package com.mr.chatgpt.components

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

@Composable
fun UserPanel() {
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
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Filled.Face, contentDescription = "record voice")
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