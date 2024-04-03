package com.example.footstamp.data.login

import android.app.Activity
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.credentials.GetCredentialException
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.getString
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.PasswordCredential
import com.example.footstamp.BuildConfig
import com.example.footstamp.R
import com.example.footstamp.data.repository.LoginRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.fido.fido2.api.common.PublicKeyCredential
import com.google.android.gms.tasks.Task
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException

class GoogleLogin(activity: Activity) {
    private val googleSignInOptions =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.GOOGLE_CLIENT_ID)
            .requestServerAuthCode(BuildConfig.GOOGLE_CLIENT_ID)
            .requestEmail()
            .build()

    val signInIntent = GoogleSignIn.getClient(activity, googleSignInOptions).signInIntent

    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>): String? {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            return account?.serverAuthCode
        } catch (e: ApiException) {
            Log.w(TAG, "signInResult:Failed code=${e.statusCode}")
        }
        return null
    }
}
