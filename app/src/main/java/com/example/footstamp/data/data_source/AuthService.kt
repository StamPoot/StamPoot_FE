package com.example.footstamp.data.data_source

import com.example.footstamp.data.dto.response.auth.AuthToken
import com.example.footstamp.data.util.RestfulManager
import com.example.footstamp.ui.base.BaseService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Response
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

    enum class Provider(val provider: String) {
        GOOGLE("google"),
        KAKAO("kakao")
    }

    companion object {
        fun loginRetrofit(): BaseService {
            return RestfulManager.getRestful()
        }
    }
}