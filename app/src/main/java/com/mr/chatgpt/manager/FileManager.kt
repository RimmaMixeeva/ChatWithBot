package com.mr.chatgpt.manager

import android.util.Log
import java.io.File

class FileManager {
    companion object {
        fun createFolder(path: String){
            var folder = File(path)
            if(!folder.exists()) {
                if(folder.mkdir()) {
                    Log.d("TEST","Folder $folder was successfully created")
                } else {
                    Log.d("TEST","Failed to create $folder folder")
                }
            }
        }

    }
}