package com.example.footstamp.data.data_source

import com.example.footstamp.data.dto.login.AuthToken
import com.example.footstamp.data.dto.login.LoginGoogleRequestModel
import com.example.footstamp.data.dto.login.LoginGoogleResponseModel
import com.example.footstamp.data.dto.login.SendAccessTokenModel
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface LoginAPI {
    @GET("/login/oauth2/code/{provider}")
    suspend fun getLoginToken(
        @Path("provider") provider: String,
        @Query("code") code: String
    ): Response<AuthToken>

    @POST("oauth2/v4/token")
    fun getAccessToken(
        @Body request: LoginGoogleRequestModel
    ): Call<LoginGoogleResponseModel>

    @POST("login")
    @Headers("content-type: application/json")
    fun sendAccessToken(
        @Body accessToken: SendAccessTokenModel
    ): Call<String>

    enum class Provider(val provider: String) {
        GOOGLE("google"),
        KAKAO("kakao")
    }

    companion object {

        private val gson = GsonBuilder().setLenient().create()

        fun loginRetrofit(baseUrl: String): LoginAPI {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(LoginAPI::class.java)
        }
    }
}