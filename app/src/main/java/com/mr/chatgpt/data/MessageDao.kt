package com.mr.chatgpt.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mr.chatgpt.utils.ChatConstants


@Dao
interface MessageDao {
    @Query("SELECT * FROM MessageTable ORDER BY data DESC, time DESC LIMIT ${ChatConstants.MAX_NUMBER_OF_MESSAGES_IN_CHAT}")
    fun getLast(): LiveData<List<DBMessage>>

    @Insert
    suspend fun insertMessage(message: DBMessage)
}