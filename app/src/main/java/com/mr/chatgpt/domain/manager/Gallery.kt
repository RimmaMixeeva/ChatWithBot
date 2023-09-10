package com.mr.chatgpt.domain.manager

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.MediaStore
import com.mr.chatgpt.domain.model.AudioModel
import com.mr.chatgpt.domain.model.VideoModel
import com.mr.chatgpt.presentation.ChatViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.nio.file.Paths


object Gallery {

    fun fillVideoList(context: Context, viewModel: ChatViewModel) {

        CoroutineScope(Dispatchers.Default).launch {
            var cursor: Cursor?
            var uri: Uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            var projection: Array<String> = arrayOf(
                MediaStore.MediaColumns.DATA,
                MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Video.Media.DURATION
            )
            var orderBy: String = MediaStore.Video.Media.DATE_TAKEN + " DESC"

            cursor = context.contentResolver.query(uri, projection, null, null, orderBy)

            var columnIndexData: Int =
                cursor?.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA) ?: -1

            var columnIndexDuration: Int =
                cursor?.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION) ?: -1

            while (cursor?.moveToNext() == true) {
                var url = cursor.getString(columnIndexData)
                val durationInMillis = cursor.getLong(columnIndexDuration)
                val uri = Uri.fromFile(File(url))
                val mediaMetadataRetriever = MediaMetadataRetriever()
                mediaMetadataRetriever.setDataSource(context, uri)
                val bitmap = mediaMetadataRetriever.frameAtTime ?: Bitmap.createBitmap(
                    10,
                    10,
                    Bitmap.Config.ARGB_8888
                )

                val newList = viewModel.videoList.value
                newList?.add(VideoModel(url, bitmap, durationInMillis))
                viewModel.videoList.postValue(newList)
            }
        }
    }

    fun fillAudioList(context: Context, viewModel: ChatViewModel) {

        CoroutineScope(Dispatchers.Default).launch {
            val cursor: Cursor?

            val uri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            val projection: Array<String> = arrayOf(
                MediaStore.MediaColumns.DATA,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ARTIST
            )
            val orderBy: String = MediaStore.Audio.Media.DATE_ADDED + " DESC"

            cursor = context.contentResolver.query(uri, projection, null, null, orderBy)

            val columnIndexData: Int =
                cursor?.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA) ?: -1
            val columnIndexDuration: Int =
                cursor?.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION) ?: -1
            val columnIndexArtist: Int =
                cursor?.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST) ?: -1

            while (cursor?.moveToNext() == true) {
                val url = cursor.getString(columnIndexData)
                val title = Paths.get(url).fileName.toString()
                val duration = cursor.getLong(columnIndexDuration)
                val artist = cursor.getString(columnIndexArtist)

                val newList = viewModel.audioList.value
                newList?.add(AudioModel(title, url, duration, artist))
                viewModel.audioList.postValue(newList)
            }
        }
    }

    fun listOfImages(context: Context): ArrayList<String> {
        var cursor: Cursor
        var listOfAllImages: ArrayList<String> = ArrayList()
        var absolutePathOfImage: String

        var uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        var projection: Array<String> =
            arrayOf(MediaStore.MediaColumns.DATA)
        var orderBy: String = MediaStore.Images.Media.DATE_TAKEN + " DESC";
        cursor = context.contentResolver.query(uri, projection, null, null, orderBy)!!;
        var columnIndexData: Int = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(columnIndexData);
            listOfAllImages.add(absolutePathOfImage)
        }

        return listOfAllImages
    }


}