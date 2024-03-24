package com.example.footstamp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.footstamp.data.login.GoogleLogin
import com.example.footstamp.ui.screen.login.LoginScreen
import com.example.footstamp.ui.theme.FootStampTheme
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    private val loginViewModel by viewModels<LoginViewModel>()
    private val googleLogin = GoogleLogin()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {
            FootStampTheme {
                LoginScreen(
                    onGoogleLogin = { googleLoginEvent() },
                    onKakaoLogin = { kakaoLoginEvent() })
            }
        }

        lifecycleScope.launch {
            loginViewModel.googleToken.collect {
                if (it != null) loginViewModel.loadData()
            }
        }

    }

    private fun googleLoginEvent() {

        googleLogin.googleLogin(this)
        googleLogin.loginEvent(this)
    }

    private fun kakaoLoginEvent() {

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            2 -> {
                try {
                    val credential = googleLogin.oneTapClient.getSignInCredentialFromIntent(data)
                    val idToken = credential.googleIdToken
                    when {
                        idToken != null -> {
                            // Got an ID token from Google. Use it to authenticate
                            // with your backend.
                            Log.d("TAG", "Got ID token.")
                            Log.d("TAG", idToken)
                            loginViewModel.updateGoogleToken(idToken)
                        }

                        else -> {
                            // Shouldn't happen.
                            Log.d("TAG", "No ID token!")
                        }
                    }
                } catch (e: ApiException) {
                    when (e.statusCode) {
                        CommonStatusCodes.CANCELED -> {
                            Log.d("TAG", "One-tap dialog was closed.")
                            // Don't re-prompt the user.
//                            showOneTapUI = false
                        }

                        CommonStatusCodes.NETWORK_ERROR -> {
                            Log.d("TAG", "One-tap encountered a network error.")
                            // Try again or just ignore.
                        }

                        else -> {
                            Log.d(
                                "TAG", "Couldn't get credential from result." +
                                        " (${e.localizedMessage})"
                            )
                        }
                    }
                }
            }
        }
    }
}