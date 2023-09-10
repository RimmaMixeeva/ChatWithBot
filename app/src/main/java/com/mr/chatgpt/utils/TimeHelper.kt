package com.mr.chatgpt.utils

import android.util.Log
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class TimeHelper {
    companion object {
        fun getCurrentTimeString(): String {
            val currentTime = LocalTime.now()
            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            return currentTime.format(formatter)
        }

        fun getCurrentTimeLong(): Long {
            val localTime = LocalTime.now()
            var seconds = localTime.toSecondOfDay()
            var milliseconds: Long = seconds * 1000L
            return milliseconds
        }


        fun convertMillisecondsToTime(milliseconds: Long): String {
            val totalSeconds = milliseconds / 1000
            val hours = totalSeconds / 3600
            val remainderSeconds = totalSeconds % 3600
            val minutes = remainderSeconds / 60

            return String.format("%02d:%02d", hours, minutes)
        }
    }
}