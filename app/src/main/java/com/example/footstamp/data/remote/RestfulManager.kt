package com.example.footstamp.data.remote

import android.util.Log
import com.example.footstamp.data.data_source.LoginAPI
import com.example.footstamp.data.repository.LoginRepository
import com.google.firebase.auth.ktx.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RestfulManager {
    @Provides
    fun provideBaseUrl() = "https://impine.shop/"

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient.Builder().build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(provideBaseUrl())
            .client(
                OkHttpClient
                    .Builder()
                    .addNetworkInterceptor { chain ->
                        chain.proceed(
                            chain.request()
                                .also { log(it) }
                                .newBuilder()
                                .build()
                        )
                    }
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun log(request: Request) {
        val url = request.url
        val headers = request.headers
        val body = request.body

        Log.d("[RESTFUL]", "RequestURL : $url")
        Log.d("[RESTFUL]", "RequestHeader : $headers")
        Log.d("[RESTFUL]", "RequestBody : $body")
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): LoginAPI {
        return retrofit.create(LoginAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideMainRepository(loginAPI: LoginAPI) = LoginRepository(loginAPI)
}