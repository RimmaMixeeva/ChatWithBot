package com.mr.chatgpt.components

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow.Companion.Clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.mr.chatgpt.R
import com.mr.chatgpt.controllers.RecordController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.lang.Integer.max


@Composable
fun recordAnimation(iconScale: Int, recordController: RecordController) {

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
    var isOn by remember{
        mutableStateOf(false)
    }

    val ms by recordController.getAmplitude().collectAsState(initial = 3)

    val movingIconScale by animateIntAsState(targetValue = ms, animationSpec = tween(durationMillis = recordController.getAmplitudeDelay()))
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

    Box(contentAlignment = Alignment.BottomEnd, modifier = Modifier.zIndex(1f).fillMaxSize()) {
    Box(contentAlignment = Alignment.Center) {

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
            recordController.stop()
            isOn = recordController.recorderIsOn()
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
