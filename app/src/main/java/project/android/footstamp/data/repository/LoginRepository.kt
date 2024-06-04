package project.android.footstamp.data.repository

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import project.android.footstamp.data.data_source.AuthService
import project.android.footstamp.data.model.LoginToken
import project.android.footstamp.data.model.Provider
import project.android.footstamp.data.util.TokenManager
import project.android.footstamp.ui.base.BaseRepository
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val tokenManager: TokenManager,
    private val authService: AuthService
) : BaseRepository() {
    private val accessToken = runBlocking {
        tokenManager.accessToken.first()
    }

    suspend fun fetchAccessToken(
        provider: Provider, token: String
    ): String? {
        authService.authLoginToken(provider.provider, token).let { response ->
            if (response.isSuccessful && response.body() != null) {
                val responseBody = response.body()!!
                tokenManager.updateAccessToken(responseBody.auth)
                return responseBody.auth
            }
        }
        return null
    }

    suspend fun setTokenDao(token: LoginToken) =
        tokenManager.updateAccessToken(token.token)

    suspend fun getTokenDao(): String? =
        accessToken

    suspend fun deleteTokenDao() =
        tokenManager.clearAccessToken()
}