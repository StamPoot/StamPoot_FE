package com.example.footstamp.data.repository

import com.example.footstamp.data.data_source.LoginAPI
import javax.inject.Inject

class LoginRepository @Inject constructor(private val loginAPI: LoginAPI) {

    suspend fun login(provider: LoginAPI.Provider, token: String) =
        loginAPI.getLoginToken(provider.provider, token)
}