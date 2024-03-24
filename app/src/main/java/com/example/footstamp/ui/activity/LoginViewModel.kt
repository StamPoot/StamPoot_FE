package com.example.footstamp.ui.activity

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.footstamp.data.data_source.LoginAPI
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

    private val _loginToken = MutableStateFlow<String?>(null)
    val loginToken = _loginToken.asStateFlow()

    fun updateGoogleToken(googleToken: String) {
        _googleToken.value = googleToken
    }

    @SuppressLint("SuspiciousIndentation")
    fun loadData() {
        viewModelScope.launch {
            val data = repository.login(LoginAPI.Provider.GOOGLE, _googleToken.value!!)
                    Log.d("TAG", "CHEESE")

            when (data.isSuccessful) {
                true -> {
                    _loginToken.value = data.body().toString()
                    Log.d("TAG", "SUCCESS")
                }

                else -> {
                    Log.d("TAG", "FAIL")
                    Log.d("TAG", data.body().toString())
                }
            }
        }
    }
}