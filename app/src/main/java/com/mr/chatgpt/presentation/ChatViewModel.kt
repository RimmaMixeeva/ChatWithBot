package com.mr.chatgpt.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mr.chatgpt.domain.model.AudioModel
import com.mr.chatgpt.domain.model.Message
import com.mr.chatgpt.domain.model.VideoModel
import kotlinx.coroutines.flow.MutableStateFlow

class ChatViewModel(application: Application) : AndroidViewModel(application)  {

    var videoList: MutableLiveData<ArrayList<VideoModel>> = MutableLiveData(ArrayList())
    var audioList: MutableLiveData<ArrayList<AudioModel>> = MutableLiveData(ArrayList())
    var chatMessage = Message();
    var galleryIsOpened: MutableLiveData<Boolean> = MutableLiveData()

}