package com.example.footstamp.ui.activity

import androidx.lifecycle.viewModelScope
import com.example.footstamp.data.model.Provider
import com.example.footstamp.data.repository.LoginRepository
import com.example.footstamp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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

    fun updateGoogleIdToken(googleToken: String) {
        _googleIdToken.value = googleToken
    }

    fun updateLoginToken(loginToken: String) {
        _loginToken.value = loginToken
    }

    fun googleAccessTokenLogin() {
        viewModelScope.launch {
            repository.accessTokenLogin(Provider.GOOGLE, _googleIdToken.value!!)
                .also { _loginToken.value = it.body()?.auth }
        }
    }

    fun kakaoAccessTokenLogin(token: String) {
        viewModelScope.launch {
            repository.accessTokenLogin(Provider.KAKAO, token)
                .also { _loginToken.value = token }
        }
    }

    fun kakaoLogin() {
        viewModelScope.launch {
            repository.kakaoLogin()
        }
    }
}