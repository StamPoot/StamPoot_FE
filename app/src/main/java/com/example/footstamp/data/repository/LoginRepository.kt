package com.example.footstamp.data.repository

import com.example.footstamp.data.dao.TokenDao
import com.example.footstamp.data.data_source.AuthService
import com.example.footstamp.data.dto.response.auth.AuthToken
import com.example.footstamp.data.model.LoginToken
import com.example.footstamp.data.model.Provider
import com.example.footstamp.data.util.TokenManager
import com.example.footstamp.ui.base.BaseRepository
import retrofit2.Response
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val tokenManager: TokenManager,
    private val authService: AuthService,
    private val tokenDao: TokenDao
) : BaseRepository() {

    suspend fun accessTokenLogin(
        provider: Provider, token: String
    ): Response<AuthToken> {
        return authService.authLoginToken(provider.provider, token).also { response ->
            if (response.isSuccessful && response.body() != null) {
                tokenManager.accessToken = response.body()?.auth
            }
        }
    }

    suspend fun kakaoLogin(): Response<AuthToken> {
        return authService.kakaoLogin()
    }

    suspend fun setTokenDao(token: LoginToken) =
        tokenDao.setToken(token)

    suspend fun getTokenDao(): LoginToken? =
        tokenDao.getToken()

    suspend fun deleteTokenDao() =
        tokenDao.deleteToken()
}