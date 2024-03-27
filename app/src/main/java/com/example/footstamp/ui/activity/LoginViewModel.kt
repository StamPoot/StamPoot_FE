package com.example.footstamp.ui.activity

import android.app.Activity
import android.content.Context
import android.credentials.GetCredentialException
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.example.footstamp.data.repository.LoginRepository
import com.example.footstamp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import com.example.footstamp.data.login.GoogleLogin
import com.google.android.gms.common.api.ApiException
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

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    fun googleIdLogin(context: Context) {
        val googleLoginManager = GoogleLogin(context)

        viewModelScope.launch {
            try {
                val result = googleLoginManager.credentialManager.getCredential(
                    context = context,
                    request = googleLoginManager.request
                )
                _googleToken.value = googleLoginManager.handleSignIn(result)
            } catch (e: GetCredentialException) {
                googleLoginManager.handleFailure(e)
            }
        }
    }

    fun googleAccessLogin(accessToken: String, activity: Activity) {
        val googleLoginManager = GoogleLogin(activity)

        viewModelScope.launch {
            try {
                val result = googleLoginManager.signIn(activity)
            } catch (e: Exception) {

            }
        }
    }

}