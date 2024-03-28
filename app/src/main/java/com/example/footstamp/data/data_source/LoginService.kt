package com.example.footstamp.data.data_source

import com.example.footstamp.data.dto.login.AuthToken
import com.example.footstamp.data.dto.login.LoginGoogleRequestModel
import com.example.footstamp.data.dto.login.LoginGoogleResponseModel
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface LoginService {
    @GET("/login/oauth2/code/{provider}")
    suspend fun getLoginToken(
        @Path("provider") provider: String,
        @Query("code") code: String
    ): Response<AuthToken>

    @POST("oauth2/v4/token")
    suspend fun fetchGoogleAuthInfo(
        @Body request: LoginGoogleRequestModel
    ): Response<LoginGoogleResponseModel>?

    enum class Provider(val provider: String) {
        GOOGLE("google"),
        KAKAO("kakao")
    }

    companion object {

        private val gson = GsonBuilder().setLenient().create()

        fun loginRetrofit(baseUrl: String): LoginService {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(LoginService::class.java)
        }
    }
}