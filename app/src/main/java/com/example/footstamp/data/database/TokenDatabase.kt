package com.example.footstamp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.footstamp.data.dao.TokenDao
import com.example.footstamp.data.model.LoginToken

@Database(entities = [LoginToken::class], version = 1)
abstract class TokenDatabase : RoomDatabase() {
    abstract fun tokenDao(): TokenDao
}