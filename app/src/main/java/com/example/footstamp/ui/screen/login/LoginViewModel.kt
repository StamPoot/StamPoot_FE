package com.example.footstamp.ui.screen.login

import com.example.footstamp.data.repository.LoginRepository
import com.example.footstamp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository
) : BaseViewModel() {

    private val _isKakaoLoginPress = MutableStateFlow(false)
    val isKakaoLoginPress = _isKakaoLoginPress.asStateFlow()

    fun pressKakaoLogin() {
        _isKakaoLoginPress.value = true
    }
}