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

    // 소셜 로그인
    @GET("/login/oauth2/code/{provider}")
    suspend fun authLoginToken(
        @Path("provider") provider: String,
        @Query("code") code: String
    ): Response<AuthToken>

    @GET("/authorize")
    suspend fun kakaoLogin(
        @Query("client_id") clientId: String = "1835b017f8241b82663c7f11a394c9cb",
        @Query("redirect_uri") redirectUri: String = BuildConfig.KAKAO_REDIRECT_URL,
        @Query("response_type") responseType: String = "code",
    ): Response<AuthToken>
}