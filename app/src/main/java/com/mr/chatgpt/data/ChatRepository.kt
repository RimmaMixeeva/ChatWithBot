package com.mr.chatgpt.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.mr.chatgpt.domain.model.Message

class ChatRepository(private val dao: MessageDao) {

    val readAllData: LiveData<List<Message>> = dao.getLast().switchMap() {
        list ->
        val transformedList = mutableListOf<Message>()

        for (item in list) {
            val transformedItem = item.toMessage()
            transformedList.add(transformedItem)
        }

        val transformedLiveData = MutableLiveData<List<Message>>()
        transformedLiveData.value = transformedList
        transformedLiveData
    }


    suspend fun insertData(message: Message) {
        dao.insertMessage(message = message.toDBMessage())
    }
}

