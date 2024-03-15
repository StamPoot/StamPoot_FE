package com.example.footstamp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.footstamp.data.dao.ProfileDao
import com.example.footstamp.data.model.Profile

@Database(entities = [Profile::class], version = 2, exportSchema = false)
abstract class ProfileDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
}