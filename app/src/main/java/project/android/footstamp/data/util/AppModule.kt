package project.android.footstamp.data.util

import android.content.Context
import androidx.room.Room
import project.android.footstamp.data.dao.DiaryDao
import project.android.footstamp.data.dao.ProfileDao
import project.android.footstamp.data.data_source.AuthService
import project.android.footstamp.data.data_source.BoardService
import project.android.footstamp.data.data_source.DiaryService
import project.android.footstamp.data.data_source.ReplyService
import project.android.footstamp.data.data_source.UserService
import project.android.footstamp.data.database.DiaryDatabase
import project.android.footstamp.data.database.ProfileDatabase
import project.android.footstamp.data.repository.BoardRepository
import project.android.footstamp.data.repository.DiaryRepository
import project.android.footstamp.data.repository.LoginRepository
import project.android.footstamp.data.repository.ProfileRepository
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
    fun provideLoginRepository(
        tokenManager: TokenManager,
        authService: AuthService
    ): LoginRepository {
        return LoginRepository(tokenManager, authService)
    }

    @Singleton
    @Provides
    fun provideDiaryRepository(
        tokenManager: TokenManager,
        diaryDao: DiaryDao,
        diaryService: DiaryService
    ): DiaryRepository {
        return DiaryRepository(tokenManager, diaryDao, diaryService)
    }

    @Singleton
    @Provides
    fun provideBoardRepository(
        tokenManager: TokenManager,
        boardService: BoardService,
        replyService: ReplyService,
        diaryService: DiaryService
    ): BoardRepository {
        return BoardRepository(tokenManager, boardService, replyService, diaryService)
    }

    @Singleton
    @Provides
    fun provideProfileRepository(
        tokenManager: TokenManager,
        profileDao: ProfileDao,
        userService: UserService
    ): ProfileRepository {
        return ProfileRepository(tokenManager, profileDao, userService)
    }

    @Singleton
    @Provides
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager {
        return TokenManager(context)
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