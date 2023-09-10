package com.mr.chatgpt.presentation.components


import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import com.mr.chatgpt.domain.manager.Gallery
import com.mr.chatgpt.presentation.ChatViewModel

@Composable
fun selectionMenu(
    backgroundColor: Color,
    textColor: Color,
    textChosenColor: Color,
    wasChosenColor: Color,
    context: Context,
    chatViewModel: ChatViewModel
) {
    var chosen by remember {
        mutableStateOf("PHOTO")
    }
    Column() {
        Row(
            modifier = Modifier
                .fillMaxWidth().background(backgroundColor),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { chosen = "PHOTO" },
                modifier = Modifier.weight(1f),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (chosen == "PHOTO") wasChosenColor else backgroundColor,
                    contentColor = if (chosen == "PHOTO") textChosenColor else textColor,
                )
            ) {
                Text(text = "PHOTO")
            }
            Button(
                onClick = { chosen = "VIDEO" },
                modifier = Modifier.weight(1f),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (chosen == "VIDEO") wasChosenColor else backgroundColor,
                    contentColor = if (chosen == "VIDEO") textChosenColor else textColor,
                )
            ) {
                Text(text = "VIDEO")
            }
            Button(
                onClick = { chosen = "AUDIO" },
                modifier = Modifier.weight(1f),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (chosen == "AUDIO")  wasChosenColor else backgroundColor,
                    contentColor = if (chosen == "AUDIO") textChosenColor else textColor,
                )
            ) {
                Text(text = "AUDIO")
            }
        }
        if (chosen == "PHOTO") {
            imageGallery(images = Gallery.listOfImages(context = context), chatViewModel)
        }
        if (chosen == "VIDEO") {
            videoGallery(chatViewModel)
        }
        if (chosen == "AUDIO") {
            audioGallery(chatViewModel)
        }
        }

}
