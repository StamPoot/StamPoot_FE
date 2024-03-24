package com.example.footstamp.data.data_source

import com.example.footstamp.data.dto.AuthToken
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LoginAPI {
    @GET("/login/oauth2/code/{provider}")
    suspend fun getLoginToken(
        @Path("provider") provider: String,
        @Query("code") code: String
    ): Response<AuthToken>

    enum class Provider(val provider: String) {
        GOOGLE("google"),
        KAKAO("kakao")
    }
}