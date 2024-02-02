package com.example.footstamp.data.util

import android.content.Context
import androidx.room.Room
import com.example.footstamp.data.dao.DiaryDao
import com.example.footstamp.data.database.DiaryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDiariesDao(diaryDatabase: DiaryDatabase): DiaryDao = diaryDatabase.diaryDao()

    @Singleton
    @Provides
    fun provideDiaryDatabase(@ApplicationContext context: Context): DiaryDatabase {
        return Room.databaseBuilder(
            context,
            DiaryDatabase::class.java,
            "diary_database"
        ).fallbackToDestructiveMigration()
            .build()
    }
}