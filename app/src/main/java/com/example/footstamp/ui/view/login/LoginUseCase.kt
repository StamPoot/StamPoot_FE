package com.example.footstamp.ui.view.login

import com.example.footstamp.data.model.LoginToken
import com.example.footstamp.data.model.Provider
import com.example.footstamp.data.repository.LoginRepository
import javax.inject.Inject

class FetchAccessTokenUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(provider: Provider, token: String): String? {
        return loginRepository.fetchAccessToken(provider, token)
    }
}

class SetTokenDaoUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(token: LoginToken) {
        loginRepository.setTokenDao(token)
    }
}

class GetTokenDaoUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(): LoginToken? {
        return loginRepository.getTokenDao()
    }
}

class DeleteTokenDaoUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke() {
        loginRepository.deleteTokenDao()
    }
}