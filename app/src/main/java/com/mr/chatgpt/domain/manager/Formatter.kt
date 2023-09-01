package com.mr.chatgpt.domain.manager

class Formatter {

    companion object {
     fun returnFormattedTime(milliseconds: Long): String {
        var seconds = milliseconds / 1000 % 60
        val minutes = milliseconds / (1000 * 60) % 60
        val hours = milliseconds / (1000 * 60 * 60) % 24
        return if (hours == 0L) {
            if (minutes == 0L && seconds == 0L) {
                seconds++
                String.format("%02d:%02d", minutes, seconds)
            } else {
                String.format("%02d:%02d", minutes, seconds)
            }
        } else {
            String.format("%02d:%02d:%02d", hours, minutes, seconds)
        }
    }
    }
}