package com.mr.chatgpt.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mr.chatgpt.data.AppDatabase
import com.mr.chatgpt.data.ChatRepository
import com.mr.chatgpt.domain.model.AudioModel
import com.mr.chatgpt.domain.model.Message
import com.mr.chatgpt.domain.model.VideoModel
import kotlinx.coroutines.launch

class ChatViewModel(application: Application) : AndroidViewModel(application)  {

    var videoList: MutableLiveData<ArrayList<VideoModel>> = MutableLiveData(ArrayList())
    var audioList: MutableLiveData<ArrayList<AudioModel>> = MutableLiveData(ArrayList())
    var chatMessage = Message();
    var galleryIsOpened: MutableLiveData<Boolean> = MutableLiveData()
    var currentSender: String = "me"


    var allMessages: LiveData<List<Message>>
    private val repository: ChatRepository

    init {
        val chatDao = AppDatabase.getDbInstance(application).messageDao()
        repository = ChatRepository(chatDao)
        allMessages = repository.readAllData
    }

    fun insertExercise(message: Message) {
        viewModelScope.launch {
            repository.insertData(message)
        }
    }

}