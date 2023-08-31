package com.mr.chatgpt.domain.manager

import android.os.Environment

class PathNames {
    companion object {
        val EXTERNAL_STORAGE_PATH: String = Environment.getExternalStorageDirectory().toString()
        val EXTERNAL_APPLICATION_APP: String = "$EXTERNAL_STORAGE_PATH/ChatGPT"
        val EXTERNAL_APPLICATION_RECORDINGS: String = "$EXTERNAL_APPLICATION_APP/Recordings"
    }
}
