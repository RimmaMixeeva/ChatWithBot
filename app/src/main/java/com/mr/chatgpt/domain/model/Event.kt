package com.mr.chatgpt.domain.model

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import java.net.HttpURLConnection
import java.net.URL

data class Event(val name: String = "", val data: String = "")

fun getEventsFlow(): Flow<Event> = flow {
    coroutineScope { // Wrap the code with this, to have access  to the coroutine scope
        val url = "https://hacker-news.firebaseio.com/v0/updates.json"

        // Gets HttpURLConnection. Blocking function.  Should run in background
        val conn = (URL(url).openConnection() as HttpURLConnection).also {
            it.setRequestProperty("Accept", "text/event-stream") // set this Header to stream
            it.doInput = true // enable inputStream
        }

        conn.connect() // Blocking function. Should run in background

        val inputReader = conn.inputStream.bufferedReader()

        var event = Event()

        // run while the coroutine is active
        while (isActive) {
            val line = inputReader.readLine() // Blocking function. Read stream until \n is found

            when {
                line.startsWith("event:") -> { // get event name
                    event = event.copy(name = line.substring(6).trim())
                }
                line.startsWith("data:") -> { // get data
                    event = event.copy(data = line.substring(5).trim())
                }
                line.isEmpty() -> { // empty line, finished block. Emit the event
                    emit(event)
                    event = Event()
                }
            }
        }
    }
}

