package project.android.footstamp.data.repository

import project.android.footstamp.data.dao.TokenDao
import project.android.footstamp.data.data_source.AuthService
import project.android.footstamp.data.model.LoginToken
import project.android.footstamp.data.model.Provider
import project.android.footstamp.data.util.TokenManager
import project.android.footstamp.ui.base.BaseRepository
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val tokenManager: TokenManager,
    private val authService: AuthService,
    private val tokenDao: TokenDao
) : BaseRepository() {

    suspend fun fetchAccessToken(
        provider: Provider, token: String
    ): String? {
        authService.authLoginToken(provider.provider, token).let { response ->
            if (response.isSuccessful && response.body() != null) {
                val responseBody = response.body()!!
                tokenManager.accessToken = responseBody.auth
                return responseBody.auth
            }
        }
        return null
    }

    suspend fun setTokenDao(token: LoginToken) =
        tokenDao.setToken(token)

    suspend fun getTokenDao(): LoginToken? =
        tokenDao.getToken()

    suspend fun deleteTokenDao() =
        tokenDao.deleteToken()
}