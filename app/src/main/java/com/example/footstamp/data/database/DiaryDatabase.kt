package com.example.footstamp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.footstamp.data.dao.DiaryDao
import com.example.footstamp.data.model.Diary
import com.example.footstamp.data.util.ListConverters

@Database(entities = [Diary::class], version = 2, exportSchema = false)
@TypeConverters(ListConverters::class)
abstract class DiaryDatabase : RoomDatabase() {
    abstract fun diaryDao(): DiaryDao
}