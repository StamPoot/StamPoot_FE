package project.android.footstamp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import project.android.footstamp.data.dao.TokenDao
import project.android.footstamp.data.model.LoginToken

@Database(entities = [LoginToken::class], version = 2, exportSchema = false)
abstract class TokenDatabase : RoomDatabase() {
    abstract fun tokenDao(): TokenDao
}