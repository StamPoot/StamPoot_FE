package com.example.footstamp.ui.activity

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.example.footstamp.BuildConfig
import com.example.footstamp.data.login.GoogleLogin
import com.example.footstamp.ui.screen.login.LoginScreen
import com.example.footstamp.ui.theme.FootStampTheme
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    private val loginViewModel by viewModels<LoginViewModel>()
    private val RC_SIGN_IN = 1001
    private lateinit var googleLogin: GoogleLogin

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
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
                if (it != null) loginViewModel.googleLogin()
            }
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // 구글 로그인 get code
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            googleLogin.handleSignInResult(task)?.let { token ->
                loginViewModel.updateGoogleToken(token)
            }
        }
    }

    private fun sendCodeToServer(code: String?) {
        Log.d(TAG, "SERVER AUTH CODE: $code")
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    private fun googleLoginEvent() {
        googleLogin = GoogleLogin(this)
        val signInIntent = googleLogin.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun kakaoLoginEvent() {

    }
}