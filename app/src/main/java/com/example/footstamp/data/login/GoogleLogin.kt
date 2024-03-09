package com.example.footstamp.data.login

import android.content.ContentValues
import android.content.Context
import android.content.IntentSender
import android.util.Log
import androidx.core.app.ActivityCompat.startIntentSenderForResult
import androidx.core.content.ContextCompat.getString
import com.example.footstamp.R
import com.example.footstamp.ui.activity.LoginActivity
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient

class GoogleLogin {

    lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest

    fun googleLogin(context: Context) {
        oneTapClient = Identity.getSignInClient(context)
        signInRequest = BeginSignInRequest.builder()
            .setPasswordRequestOptions(
                BeginSignInRequest.PasswordRequestOptions.builder()
                    .setSupported(true)
                    .build()
            )
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Your server's client ID, not your Android client ID.
                    .setServerClientId(getString(context, R.string.web_client_id))
                    // Only show accounts previously used to sign in.
                    .setFilterByAuthorizedAccounts(true)
                    .build()
            )
            // Automatically sign in when exactly one credential is retrieved.
            .setAutoSelectEnabled(true)
            .build()
    }

    fun loginEvent(activity: LoginActivity) {
        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener(activity) { result ->
                try {
                    startIntentSenderForResult(
                        activity,
                        result.pendingIntent.intentSender, 2,
                        null, 0, 0, 0, null
                    )
                } catch (e: IntentSender.SendIntentException) {
                    Log.e(ContentValues.TAG, "Couldn't start One Tap UI: ${e.localizedMessage}")
                }
            }
            .addOnFailureListener(activity) { e ->
                // No saved credentials found. Launch the One Tap sign-up flow, or
                // do nothing and continue presenting the signed-out UI.
                Log.d(ContentValues.TAG, "0.3")
            }
    }
}