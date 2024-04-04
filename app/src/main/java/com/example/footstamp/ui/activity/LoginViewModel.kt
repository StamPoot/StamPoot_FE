package com.example.footstamp.ui.activity

import androidx.lifecycle.viewModelScope
import com.example.footstamp.data.data_source.LoginService
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
    private val _googleToken = MutableStateFlow<String?>(null)
    val googleToken = _googleToken.asStateFlow()

    private val _accessToken = MutableStateFlow<String?>(null)
    val accessToken = _accessToken.asStateFlow()

    fun updateGoogleToken(googleToken: String) {
        _googleToken.value = googleToken
    }

    fun updateLoginToken(loginToken: String) {
        _accessToken.value = loginToken
    }

    fun googleAccessTokenLogin() {
        viewModelScope.launch {
            repository.googleAccessTokenLogin(LoginService.Provider.GOOGLE, _googleToken.value!!)
                .also { _accessToken.value = it.body()?.auth }
        }
    }
}