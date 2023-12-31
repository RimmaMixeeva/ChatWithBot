package com.mr.chatgpt.presentation.components


import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mr.chatgpt.R
import com.mr.chatgpt.domain.manager.Formatter
import com.mr.chatgpt.domain.model.AudioModel
import com.mr.chatgpt.presentation.ChatViewModel
import com.mr.chatgpt.ui.theme.DarkFill
import com.mr.chatgpt.ui.theme.LightBlack
import com.mr.chatgpt.ui.theme.LightGrey
import com.mr.chatgpt.ui.theme.LightYellow


@Composable
fun audioGallery(viewModel: ChatViewModel) {

    var chosenAudio by remember {
        mutableStateOf("")
    }

    var chosenAudioIndex: Int = 0

    var audioList by remember { mutableStateOf<List<AudioModel>?>(null) }

    var listSize by remember { mutableStateOf(0) }

    viewModel.audioList.observe(LocalContext.current as ComponentActivity) {
        audioList = it
        listSize = it.size
    }

    Box() {

        LazyColumn(
            reverseLayout = true,
            contentPadding = PaddingValues(6.dp),
            modifier = Modifier.background(
                DarkFill
            )
        ) {
            items(listSize) { index ->
                AudioItem(audio = audioList?.get(index), chosenAudio) {
                    chosenAudio = if (chosenAudio == audioList?.get(index)?.url) {
                        chosenAudioIndex = 0
                        ""
                    } else {
                        chosenAudioIndex = index
                        audioList?.get(index)?.url ?: ""
                    }
                }
            }
        }

        if (chosenAudio != "") {
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
                        chosenAudio = ""
                        viewModel.chatMessage.video = null
                        viewModel.chatMessage.photo = null
                        viewModel.chatMessage.audio = audioList?.get(chosenAudioIndex)
                        viewModel.galleryIsOpened.postValue(false)
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

@Composable
fun AudioItem(audio: AudioModel?, chosenAudio: String, onClickListener: () -> Unit) {

    if (audio != null) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .background(if (chosenAudio == audio.url) LightYellow else LightBlack)
                .clickable(onClick = onClickListener),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(modifier = Modifier.padding(4.dp)) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.music_note),
                    contentDescription = "video icon",
                    tint = LightBlack,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(LightYellow)
                )
                Column(modifier = Modifier.padding(4.dp, 0.dp)) {
                    Text(
                        text = audio.name, fontSize = 15.sp, maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.widthIn(max = 200.dp),
                        color = if (chosenAudio == audio.url) LightBlack else LightGrey
                    )
                    Text(
                        text = audio.artist, maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.widthIn(max = 200.dp),
                        color = if (chosenAudio == audio.url) LightBlack else LightGrey
                    )
                }
            }

            if (chosenAudio == audio.url) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.check),
                    contentDescription = "check",
                    tint = LightBlack,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(LightYellow)
                        .padding(4.dp)
                )
            } else {
                Text(
                    text = Formatter.returnFormattedTime(audio.durationInMillis),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(4.dp),
                    color = LightGrey
                )
            }

        }

    }
}