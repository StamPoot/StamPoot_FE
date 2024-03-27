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

class GoogleLogin(context: Context) {
    private val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(true)
        .setServerClientId(getString(context, R.string.web_client_id))
        .build()
    val request: GetCredentialRequest = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()
    val credentialManager = CredentialManager.create(context)
    private val googleSignInOption =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(context, R.string.web_client_id))
            .requestServerAuthCode(getString(context, R.string.web_client_id))
            .requestEmail()
            .build()
    private val googleSignInClient = GoogleSignIn.getClient(context, googleSignInOption)


    // google idToken
    fun handleSignIn(result: GetCredentialResponse): String? {

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
                        return googleIdTokenCredential.idToken
                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e(ContentValues.TAG, "Invalid Value Token", e)
                    }
                } else {
                    Log.e(ContentValues.TAG, "Unexpected type of credential")
                }
            }
        }
        return null
    }

    fun handleFailure(e: GetCredentialException) {
        Log.d(ContentValues.TAG, "fail")
    }

    // google accessToken
    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val authCode: String? =
                completedTask.getResult(ApiException::class.java)?.serverAuthCode
        } catch (e: ApiException) {
            Log.e(TAG, "handleSignInResult: error ${e.statusCode}")
        }
    }

    fun signIn(activity: Activity) {
        val sigInIntent: Intent = googleSignInClient.signInIntent
        activity.startActivityForResult(sigInIntent, 1000)
    }

    fun signOut(context: Context) {
        googleSignInClient.signOut()
            .addOnCompleteListener {
                Toast.makeText(context, "로그아웃 되셨습니다!", Toast.LENGTH_SHORT).show()
            }
    }

    fun isLogin(context: Context): Boolean {
        val account = GoogleSignIn.getLastSignedInAccount(context)
        return account != null
    }
}
