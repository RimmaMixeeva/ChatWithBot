package com.mr.chatgpt.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface MessageDao {
    @Query("SELECT * FROM MessageTable ORDER BY time DESC LIMIT 100;")
    fun getLast(): LiveData<List<DBMessage>>

    @Insert
    suspend fun insertMessage(message: DBMessage)
}