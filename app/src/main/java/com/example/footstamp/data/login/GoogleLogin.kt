package com.example.footstamp.data.login

import android.content.ContentValues
import android.content.Context
import android.content.IntentSender
import android.credentials.GetCredentialException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat.startIntentSenderForResult
import androidx.core.content.ContextCompat.getString
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.PasswordCredential
import androidx.lifecycle.viewModelScope
import com.example.footstamp.R
import com.example.footstamp.ui.activity.LoginActivity
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.fido.fido2.api.common.PublicKeyCredential
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import kotlinx.coroutines.launch

class GoogleLogin(context: Context) {
    private val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(true)
        .setServerClientId(getString(context, R.string.web_client_id))
        .build()
    val request: GetCredentialRequest = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()
    val credentialManager = CredentialManager.create(context)

    fun handleSignIn(result: GetCredentialResponse) {

        when (val credential = result.credential) {
            is PublicKeyCredential -> {
                // responseJson = credential.authenticationResponseJson
            }

            is PasswordCredential -> {

                val username = credential.id
                val password = credential.password
                Log.d(ContentValues.TAG, "id: $username password: $password")
            }

            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential =
                            GoogleIdTokenCredential.createFrom(credential.data)
                        Log.d(ContentValues.TAG, "credential ${googleIdTokenCredential.id}")
                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e(ContentValues.TAG, "Invalid Value Token", e)
                    }
                } else {
                    Log.e(ContentValues.TAG, "Unexpected type of credential")
                }
            }
        }
    }

    fun handleFailure(e: GetCredentialException) {
        Log.d(ContentValues.TAG, "fail")
    }


}