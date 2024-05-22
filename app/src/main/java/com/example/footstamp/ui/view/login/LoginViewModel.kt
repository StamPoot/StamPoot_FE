package com.example.footstamp.ui.view.login

import com.example.footstamp.data.repository.DiaryRepository
import com.example.footstamp.data.repository.ProfileRepository
import com.example.footstamp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : BaseViewModel() {

    private val _isShowWebView = MutableStateFlow(false)
    val isShowWebView = _isShowWebView.asStateFlow()

    fun showKakaoLogin() {
        _isShowWebView.value = true
    }

    fun hideKakaoLogin() {
        _isShowWebView.value = false
    }
}