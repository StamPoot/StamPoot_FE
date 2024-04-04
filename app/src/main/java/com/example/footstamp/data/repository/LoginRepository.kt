package com.example.footstamp.data.repository

import com.example.footstamp.BuildConfig
import com.example.footstamp.data.data_source.LoginService
import com.example.footstamp.data.dto.login.AuthToken
import retrofit2.Response
import javax.inject.Inject

class LoginRepository @Inject constructor(private val service: LoginService) {
    suspend fun googleAccessTokenLogin(
        provider: LoginService.Provider,
        token: String
    ): Response<AuthToken> {
        return LoginService.loginRetrofit(BuildConfig.GOOGLE_BASE_URL)
            .getLoginToken(provider.provider, token)
    }
}