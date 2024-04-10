package com.example.footstamp.data.repository

import com.example.footstamp.data.data_source.AuthService
import com.example.footstamp.data.model.Provider
import com.example.footstamp.ui.base.BaseRepository
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val authService: AuthService
) : BaseRepository() {
    suspend fun googleAccessTokenLogin(
        provider: Provider, token: String
    ) = authService.authLoginToken(provider.provider, token)
}