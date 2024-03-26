package com.example.footstamp.ui.activity

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.credentials.GetCredentialException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.PasswordCredential
import androidx.lifecycle.viewModelScope
import com.example.footstamp.data.data_source.LoginAPI
import com.example.footstamp.data.repository.LoginRepository
import com.example.footstamp.ui.base.BaseViewModel
import com.google.android.gms.fido.fido2.api.common.PublicKeyCredential
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.credentials.GetCredentialResponse
import com.example.footstamp.data.login.GoogleLogin
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
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

    fun updateLoginToken(loginToken: String) {
        _loginToken.value = loginToken
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    fun googleLogin(context: Context) {
        val googleLoginManager = GoogleLogin(context)

        viewModelScope.launch {
            try {
                val result = googleLoginManager.credentialManager.getCredential(
                    context = context,
                    request = googleLoginManager.request
                )
                googleLoginManager.handleSignIn(result)
            } catch (e: GetCredentialException) {
                googleLoginManager.handleFailure(e)
            }
        }
    }

}