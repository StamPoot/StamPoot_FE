package com.example.footstamp.data.util

import android.content.Context
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat.getString
import androidx.room.Room
import com.example.footstamp.R
import com.example.footstamp.data.dao.DiaryDao
import com.example.footstamp.data.dao.ProfileDao
import com.example.footstamp.data.data_source.LoginService
import com.example.footstamp.data.database.DiaryDatabase
import com.example.footstamp.data.database.ProfileDatabase
import com.google.firebase.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDiariesDao(diaryDatabase: DiaryDatabase): DiaryDao = diaryDatabase.diaryDao()

    @Singleton
    @Provides
    fun provideProfileDao(profileDatabase: ProfileDatabase): ProfileDao =
        profileDatabase.profileDao()

    @Singleton
    @Provides
    fun provideDiaryDatabase(@ApplicationContext context: Context): DiaryDatabase {
        return Room.databaseBuilder(
            context,
            DiaryDatabase::class.java,
            "diary_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideProfileDatabase(@ApplicationContext context: Context): ProfileDatabase {
        return Room.databaseBuilder(
            context,
            ProfileDatabase::class.java,
            "profile_database"
        ).fallbackToDestructiveMigration().build()
    }
}