package com.mr.chatgpt.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mr.chatgpt.R
import com.mr.chatgpt.domain.manager.Formatter
import com.mr.chatgpt.domain.model.VideoModel

@Composable
fun ShowVideo(video: VideoModel, size: Dp){
    Box(
        modifier = Modifier
            .size(size)
            .clip(shape = RoundedCornerShape(4.dp))
            .padding(4.dp, 4.dp, 4.dp, 4.dp)
    ) {

        Image(
            bitmap = video.bitmap!!.asImageBitmap(),
            contentDescription = "video",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(shape = RoundedCornerShape(4.dp))
        )

        Box(
            modifier = Modifier
                .padding(4.dp)
                .align(Alignment.BottomStart)
        ) {
            Row(
                modifier = Modifier
                    .background(
                        colorResource(id = R.color.transparentBlack),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .align(Alignment.BottomStart),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.play_arrow),
                    contentDescription = "audio icon",
                    tint = Color.White,
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    text = Formatter.returnFormattedTime(video.durationInMillis),
                    modifier = Modifier.padding(0.dp, 0.dp, 4.dp, 0.dp),
                    color = Color.White,
                    fontSize = 12.sp
                )
            }
        }
    }
}