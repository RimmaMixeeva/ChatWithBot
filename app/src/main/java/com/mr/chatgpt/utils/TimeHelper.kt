package com.mr.chatgpt.utils

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
            val nanos = localTime.toNanoOfDay()
            val seconds = localTime.toSecondOfDay()

            return seconds * 1000 + nanos / 1_000_000
        }


        fun convertTimeToMilliseconds(timeString: String): Long {
            val parts = timeString.split(":")
            val hours = parts[0].toInt()
            val minutes = parts[1].toInt()

            val totalMilliseconds: Long = (hours * 60 * 60 * 1000L) + (minutes * 60 * 1000)
            return totalMilliseconds
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