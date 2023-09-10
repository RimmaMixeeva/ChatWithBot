package com.mr.chatgpt.presentation.components


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon

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
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.mr.chatgpt.R
import com.mr.chatgpt.presentation.ChatViewModel
import com.mr.chatgpt.ui.theme.DarkFill
import com.mr.chatgpt.ui.theme.LightBlack
import com.mr.chatgpt.ui.theme.LightGrey
import com.mr.chatgpt.ui.theme.LightYellow

@Composable
fun imageGallery(images: ArrayList<String>, viewModel: ChatViewModel) {

    var chosenImage by remember {
        mutableStateOf("")
    }

    Box() {
        Column(modifier = Modifier.background(DarkFill)) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 100.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                items(images.size) { index ->
                    val painter = rememberAsyncImagePainter(model = images[index])
                    val transparentWhite = Color(0x00FFFFFF)

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(2.dp, 2.dp, 1.dp, 1.dp)
                    ) {
                        Box(
                            modifier = Modifier.background(LightBlack)
                        ) {
                            Canvas(
                                modifier = Modifier
                                    .size(28.dp)
                                    .zIndex(2f)
                                    .align(Alignment.TopEnd)
                                    .padding(0.dp, 8.dp, 8.dp, 0.dp)
                                    .clickable(onClick = {
                                        chosenImage =
                                            if (chosenImage == images[index]) "" else images[index]
                                    })
                                    .clip(CircleShape)
                                    .background(if (chosenImage == images[index]) LightYellow else transparentWhite),

                                ) {
                                drawCircle(
                                    color = Color.White,
                                    center = Offset(size.width / 2, size.height / 2),
                                    radius = size.width / 2,
                                    style = Stroke(width = 3.dp.toPx())
                                )
                            }

                            Image(
                                painter = painter,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(120.dp)
                                    .clip(shape = RoundedCornerShape(4.dp))
                                    .padding(if (chosenImage == images[index]) 10.dp else 0.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }

        }
        if (chosenImage != "") {
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .clip(CircleShape)
                    .size(50.dp)
                    .background(LightYellow)
                    .align(
                        Alignment.BottomEnd
                    )
                    .clickable {
                        viewModel.chatMessage.audio = null
                        viewModel.chatMessage.video = null
                        viewModel.chatMessage.photo = chosenImage
                        viewModel.galleryIsOpened.postValue(false)
                        chosenImage = ""
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.send),
                    contentDescription = "send",
                    modifier = Modifier.padding(5.dp, 0.dp, 0.dp, 0.dp),
                    tint = DarkFill
                )
            }
        }
    }


}
