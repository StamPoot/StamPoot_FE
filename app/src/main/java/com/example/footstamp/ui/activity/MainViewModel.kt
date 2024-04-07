package com.example.footstamp.ui.activity

import com.example.footstamp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel() {
    private val _accessToken = MutableStateFlow<String>("")
    val accessToken = _accessToken.value

    fun updateAccessToken(accessToken: String) {
        _accessToken.value = accessToken
    }
}
