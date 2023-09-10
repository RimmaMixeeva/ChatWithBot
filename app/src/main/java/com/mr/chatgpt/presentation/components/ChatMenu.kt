package com.mr.chatgpt.presentation.components

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.mr.chatgpt.ui.theme.LightYellow
import kotlin.system.exitProcess

@Composable
fun chatMenu(activity: Activity) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().background(
       LightYellow
    ), horizontalArrangement = Arrangement.SpaceBetween) {
        IconButton(onClick = { activity.finish()}) {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "arrow back")
        }
        Text(text = "Assistant", textAlign = TextAlign.Center, fontSize = 30.sp)
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Filled.Menu, contentDescription = "menu", modifier = Modifier.wrapContentWidth())
        }
    }
}
