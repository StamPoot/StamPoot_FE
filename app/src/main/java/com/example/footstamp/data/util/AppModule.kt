package com.example.footstamp.data.util

import android.content.Context
import androidx.room.Room
import com.example.footstamp.data.dao.DiaryDao
import com.example.footstamp.data.dao.ProfileDao
import com.example.footstamp.data.data_source.AuthService
import com.example.footstamp.data.data_source.BoardService
import com.example.footstamp.data.data_source.DiaryService
import com.example.footstamp.data.data_source.ReplyService
import com.example.footstamp.data.data_source.UserService
import com.example.footstamp.data.database.DiaryDatabase
import com.example.footstamp.data.database.ProfileDatabase
import com.example.footstamp.data.repository.BoardRepository
import com.example.footstamp.data.repository.DiaryRepository
import com.example.footstamp.data.repository.LoginRepository
import com.example.footstamp.data.repository.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Singleton
    @Provides
    fun provideDiaryDao(diaryDatabase: DiaryDatabase): DiaryDao = diaryDatabase.diaryDao()

    @Singleton
    @Provides
    fun provideDiaryService(retrofit: Retrofit): DiaryService {
        return retrofit.create(DiaryService::class.java)
    }

    @Singleton
    @Provides
    fun provideBoardService(retrofit: Retrofit): BoardService {
        return retrofit.create(BoardService::class.java)
    }

    @Singleton
    @Provides
    fun provideReplyService(retrofit: Retrofit): ReplyService {
        return retrofit.create(ReplyService::class.java)
    }

    @Singleton
    @Provides
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Singleton
    @Provides
    fun provideProfileDao(profileDatabase: ProfileDatabase): ProfileDao =
        profileDatabase.profileDao()

    @Singleton
    @Provides
    fun provideLoginRepository(authService: AuthService) = LoginRepository(authService)


    @Singleton
    @Provides
    fun provideDiaryRepository(diaryDao: DiaryDao, diaryService: DiaryService): DiaryRepository {
        return DiaryRepository(diaryDao, diaryService)
    }

    @Singleton
    @Provides
    fun provideBoardRepository(boardService: BoardService): BoardRepository {
        return BoardRepository(boardService)
    }

    @Singleton
    @Provides
    fun provideProfileRepository(
        profileDao: ProfileDao,
        userService: UserService
    ): ProfileRepository {
        return ProfileRepository(profileDao, userService)
    }

    @Singleton
    @Provides
    fun provideDiaryDatabase(@ApplicationContext context: Context): DiaryDatabase {
        return Room.databaseBuilder(
            context, DiaryDatabase::class.java, "diary_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideProfileDatabase(@ApplicationContext context: Context): ProfileDatabase {
        return Room.databaseBuilder(
            context, ProfileDatabase::class.java, "profile_database"
        ).fallbackToDestructiveMigration().build()
    }
}