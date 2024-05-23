package com.example.footstamp.data.data_source

import com.example.footstamp.BuildConfig
import com.example.footstamp.data.dto.response.auth.AuthToken
import com.example.footstamp.ui.base.BaseService
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthService : BaseService {

    @GET("/login/oauth2/code/{provider}")
    suspend fun authLoginToken(
        @Path("provider") provider: String,
        @Query("code") code: String
    ): Response<AuthToken>
}