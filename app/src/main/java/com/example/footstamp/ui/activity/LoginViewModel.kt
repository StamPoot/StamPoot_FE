package com.example.footstamp.ui.activity

import com.example.footstamp.data.model.LoginToken
import com.example.footstamp.data.model.Provider
import com.example.footstamp.data.repository.LoginRepository
import com.example.footstamp.data.util.Formatter
import com.example.footstamp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: LoginRepository) :
    BaseViewModel() {
    private val _googleIdToken = MutableStateFlow<String?>(null)
    val googleIdToken = _googleIdToken.asStateFlow()

    private val _kakaoIdToken = MutableStateFlow<String?>(null)
    val kakaoIdToken = _kakaoIdToken.asStateFlow()

    private val _loginToken = MutableStateFlow<String?>(null)
    val loginToken = _loginToken.asStateFlow()

    init {
        getTokenFromDB()
    }

    private fun getTokenFromDB() {
        coroutineLoading {
            repository.getTokenDao().let { token ->
                if (token != null) _loginToken.value = token.token
            }
        }
    }

    fun updateGoogleIdToken(googleToken: String) {
        _googleIdToken.value = googleToken
    }

    fun updateLoginToken(loginToken: String) {
        _loginToken.value = loginToken
    }

    fun googleAccessTokenLogin() {
        coroutineLoading {
            repository.accessTokenLogin(Provider.GOOGLE, _googleIdToken.value!!)
                .also {
                    val loginToken = LoginToken(
                        provider = Provider.GOOGLE,
                        date = Formatter.localTimeToDiaryString(LocalDateTime.now()),
                    ).apply { it.body()?.auth?.let { token -> insertToken(token) } }
                    repository.setTokenDao(loginToken)
                    _loginToken.value = it.body()?.auth
                }
        }
    }

    fun kakaoAccessTokenLogin(token: String) {
        coroutineLoading {
            repository.accessTokenLogin(Provider.KAKAO, token)
                .also { _loginToken.value = token }
        }
    }

    fun kakaoLogin() {
        coroutineLoading {
            repository.kakaoLogin()
        }
    }
}