package com.example.footstamp.data.util

import android.util.Log
import com.example.footstamp.BuildConfig
import com.example.footstamp.data.data_source.AuthService
import com.example.footstamp.data.data_source.BoardService
import com.example.footstamp.data.data_source.DiaryService
import com.example.footstamp.data.data_source.ReplyService
import com.example.footstamp.data.data_source.UserService
import com.example.footstamp.data.repository.LoginRepository
import com.example.footstamp.data.repository.ProfileRepository
import com.example.footstamp.ui.base.BaseService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RestfulModule {

    @Singleton
    private fun log(request: Request) {
        val url = request.url
        val headers = request.headers
        val body = request.body

        Log.d("[RESTFUL]", "RequestURL : $url")
        Log.d("[RESTFUL]", "RequestHeader : $headers")
        Log.d("[RESTFUL]", "RequestBody : $body")
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).apply {
            level = if (com.google.firebase.BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
            .addNetworkInterceptor { chain ->
                chain.proceed(chain.request().also { request ->
                    log(request)
                }.newBuilder().build())
            }.connectTimeout(5000L, TimeUnit.SECONDS).readTimeout(5000L, TimeUnit.SECONDS)
            .writeTimeout(5000L, TimeUnit.SECONDS).build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit {
        val gson = GsonBuilder().setLenient().create()

        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(ScalarsConverterFactory.create()).client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL).build()
    }

    @Singleton
    @Provides
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }
    @Singleton
    @Provides
    fun provideBoardService(retrofit: Retrofit): BoardService {
        return retrofit.create(BoardService::class.java)
    }
    @Singleton
    @Provides
    fun provideDiaryService(retrofit: Retrofit): DiaryService {
        return retrofit.create(DiaryService::class.java)
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
    fun provideLoginRepository(authService: AuthService) = LoginRepository(authService)
}