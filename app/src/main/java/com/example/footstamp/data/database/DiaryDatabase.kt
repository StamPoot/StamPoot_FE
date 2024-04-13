package com.example.footstamp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.footstamp.data.dao.DiaryDao
import com.example.footstamp.data.model.Diary
import com.example.footstamp.data.util.ListConverters

@Database(entities = [Diary::class], version = 3, exportSchema = false)
@TypeConverters(ListConverters::class)
abstract class DiaryDatabase : RoomDatabase() {
    abstract fun diaryDao(): DiaryDao
}