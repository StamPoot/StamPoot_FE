package com.example.footstamp.data.repository

import com.example.footstamp.BuildConfig
import com.example.footstamp.data.data_source.LoginService
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val service: LoginService
) {
    suspend fun googleLogin(provider: LoginService.Provider, token: String) =
        LoginService.loginRetrofit(BuildConfig.GOOGLE_BASE_URL)
            .getLoginToken(provider.provider, token)

}