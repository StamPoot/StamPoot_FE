package project.android.footstamp.ui.view.login

import project.android.footstamp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : BaseViewModel() {

    private val _isShowPolicy = MutableStateFlow(false)
    val isShowPolicy = _isShowPolicy.asStateFlow()

    private val _isShowWebView = MutableStateFlow(false)
    val isShowWebView = _isShowWebView.asStateFlow()

    fun showPolicy() {
        _isShowPolicy.value = true
    }

    fun hidePolicy() {
        _isShowPolicy.value = false
    }

    fun showKakaoLogin() {
        _isShowWebView.value = true
    }

    fun hideKakaoLogin() {
        _isShowWebView.value = false
    }
}