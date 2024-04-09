package com.example.footstamp.data.repository

import com.example.footstamp.BuildConfig
import com.example.footstamp.data.data_source.AuthService
import com.example.footstamp.data.dto.response.auth.AuthToken
import com.example.footstamp.data.util.RestfulManager
import retrofit2.Response
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val authService: AuthService
) {
    suspend fun googleAccessTokenLogin(
        provider: AuthService.Provider, token: String
    ): Response<AuthToken> {
        return authService.authLoginToken(provider.provider, token)
    }
}