package com.mr.chatgpt.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DBMessage::class], version = 1, exportSchema = true)
    abstract class AppDatabase : RoomDatabase() {
        abstract fun messageDao(): MessageDao
        companion object {
            @Volatile
            private var INSTANCE: AppDatabase? = null

            fun getDbInstance(context: Context): AppDatabase {
                val tempInstance = INSTANCE
                if (tempInstance != null)    {
                    return tempInstance
                }
                synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "our_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                    return instance
                }}
        }
    }
