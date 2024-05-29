package project.android.footstamp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import project.android.footstamp.data.dao.DiaryDao
import project.android.footstamp.data.model.Diary
import project.android.footstamp.data.util.ListConverters

@Database(entities = [Diary::class], version = 4, exportSchema = false)
@TypeConverters(ListConverters::class)
abstract class DiaryDatabase : RoomDatabase() {
    abstract fun diaryDao(): DiaryDao
}