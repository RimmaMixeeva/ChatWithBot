package com.mr.chatgpt.presentation.components

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.mr.chatgpt.R
import com.mr.chatgpt.domain.manager.Formatter
import com.mr.chatgpt.domain.model.VideoModel
import com.mr.chatgpt.presentation.ChatViewModel
import com.mr.chatgpt.ui.theme.DarkFill
import com.mr.chatgpt.ui.theme.LightBlack
import com.mr.chatgpt.ui.theme.LightGrey
import com.mr.chatgpt.ui.theme.LightYellow
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun videoGallery(viewModel: ChatViewModel) {

    var chosenVideo by remember {
        mutableStateOf("")
    }

    var videoList by remember { mutableStateOf<List<VideoModel>?>(null) }

    var listSize by remember { mutableStateOf(0) }

    viewModel.videoList.observe(LocalContext.current as ComponentActivity) {
        videoList = it
        listSize = it.size
    }

    Column() {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 100.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.background(DarkFill)
        ) {
            items(listSize) { index ->
                val transparentWhite = Color(0x00FFFFFF)

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(2.dp, 2.dp, 1.dp, 1.dp)
                        .background(LightBlack)
                ) {
                    Canvas(
                        modifier = Modifier
                            .size(28.dp)
                            .zIndex(2f)
                            .align(Alignment.TopEnd)
                            .padding(0.dp, 8.dp, 8.dp, 0.dp)
                            .clickable(onClick = {
                                chosenVideo =
                                    if (chosenVideo == videoList?.get(index)?.url) "" else videoList?.get(
                                        index
                                    )?.url ?: ""
                            })
                            .clip(CircleShape)
                            .background(if (chosenVideo == videoList?.get(index)?.url) LightYellow else transparentWhite),

                        ) {
                        drawCircle(
                            color = Color.White,
                            center = Offset(size.width / 2, size.height / 2),
                            radius = size.width / 2,
                            style = Stroke(width = 3.dp.toPx())
                        )
                    }
                    VideoThumbnail(videoList?.get(index)!!, chosenVideo)
                }
            }
        }
    }
}

@Composable
fun VideoThumbnail(video: VideoModel, chosenVideo: String) {
    Box(
        modifier = Modifier
            .size(120.dp)
            .clip(shape = RoundedCornerShape(4.dp))
            .padding(if (chosenVideo == video.url) 10.dp else 0.dp)
    ) {

        Image(
            bitmap = video.bitmap.asImageBitmap(),
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

