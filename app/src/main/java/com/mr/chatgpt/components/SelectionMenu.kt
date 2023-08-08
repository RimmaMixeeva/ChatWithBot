package com.mr.chatgpt.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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


@Composable
fun selectionMenu(
    backgroundColor: Color,
    textColor: Color,
    wasChosenColor: Color
) {
    var chosen by remember {
        mutableStateOf("PHOTO")
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = { chosen = "PHOTO" },
            modifier = Modifier.weight(1f),
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (chosen == "PHOTO")backgroundColor else wasChosenColor,
                contentColor = textColor,
            )
        ) {
            Text(text = "PHOTO")
        }
        Button(
            onClick = { chosen = "VIDEO" },
            modifier = Modifier.weight(1f),
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (chosen == "VIDEO") backgroundColor else wasChosenColor,
                contentColor = textColor,
            )
        ) {
            Text(text = "VIDEO")
        }
        Button(
            onClick = { chosen = "AUDIO" },
            modifier = Modifier.weight(1f),
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (chosen == "AUDIO") backgroundColor else wasChosenColor,
                contentColor = textColor,
            )
        ) {
            Text(text = "AUDIO")
        }
    }
}

