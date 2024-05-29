package project.android.footstamp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import project.android.footstamp.data.dao.ProfileDao
import project.android.footstamp.data.model.Profile

@Database(entities = [Profile::class], version = 3, exportSchema = false)
abstract class ProfileDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
}