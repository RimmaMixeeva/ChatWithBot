package com.mr.chatgpt.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mr.chatgpt.R
import com.mr.chatgpt.domain.model.AudioModel
import com.mr.chatgpt.ui.theme.DarkFill
import com.mr.chatgpt.ui.theme.LightBlack
import com.mr.chatgpt.ui.theme.LightYellow

@Composable
fun ShowAudio(audio: AudioModel?) {

    if (audio != null) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .background(LightYellow),
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
                )
                Column(modifier = Modifier.padding(4.dp, 0.dp)) {
                    Text(
                        text = audio.name, fontSize = 15.sp, maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.widthIn(max = 200.dp),
                        color = DarkFill
                    )
                    Text(
                        text = audio.artist, maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.widthIn(max = 200.dp),
                        color = DarkFill
                    )
                }
            }

        }

    }
}