package com.mr.chatgpt.domain.manager

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.MediaStore
import androidx.core.util.TimeUtils.formatDuration
import com.mr.chatgpt.domain.model.VideoModel
import com.mr.chatgpt.presentation.ChatViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

object Gallery {

    fun fillVideoList(context: Context, viewModel: ChatViewModel) {

        CoroutineScope(Dispatchers.Default).launch {
            var cursor: Cursor
            var uri: Uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            var projection: Array<String> = arrayOf(
                MediaStore.MediaColumns.DATA,
                MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Video.Media.DURATION
            )
            var orderBy: String = MediaStore.Video.Media.DATE_TAKEN + " DESC"

            cursor = context.contentResolver.query(uri, projection, null, null, orderBy)!!

            var columnIndexData: Int = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
            var columnIndexFolderName: Int =
                cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME)
            var columnIndexDuration: Int =
                cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)

            while (cursor.moveToNext()) {
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

    fun listOfImages(context: Context): ArrayList<String> {
        var cursor: Cursor
        var listOfAllImages: ArrayList<String> = ArrayList()
        var absolutePathOfImage: String

        var uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        var projection: Array<String> =
            arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        var orderBy: String = MediaStore.Images.Media.DATE_TAKEN + " DESC";
        cursor = context.contentResolver.query(uri, projection, null, null, orderBy)!!;
        var columnIndexData: Int = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        var columnIndexFolderName: Int =
            cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(columnIndexData);
            listOfAllImages.add(absolutePathOfImage)
        }

        return listOfAllImages
    }

    fun listOfVideo(context: Context): ArrayList<String> {
        var cursor: Cursor
        var listOfAllVideos: ArrayList<String> = ArrayList()
        var absolutePathOfVideo: String

        var uri: Uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        var projection: Array<String> = arrayOf(MediaStore.MediaColumns.DATA)
        var orderBy: String = MediaStore.Video.Media.DATE_TAKEN + " DESC";
        cursor = context.contentResolver.query(uri, projection, null, null, orderBy)!!;
        var columnIndexData: Int = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        while (cursor.moveToNext()) {
            absolutePathOfVideo = cursor.getString(columnIndexData);
            listOfAllVideos.add(absolutePathOfVideo)
        }
        return listOfAllVideos
    }
}