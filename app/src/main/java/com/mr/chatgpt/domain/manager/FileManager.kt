package com.mr.chatgpt.domain.manager

import android.util.Log
import java.io.File
import java.net.URI
import java.nio.file.Paths

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

        fun getFileNameFromUrl(url: String): String {
            return Paths.get(url).fileName.toString()
        }

    }
}