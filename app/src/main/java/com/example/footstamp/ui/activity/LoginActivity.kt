package com.example.footstamp.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.footstamp.data.login.GoogleLogin
import com.example.footstamp.ui.screen.login.LoginScreen
import com.example.footstamp.ui.theme.FootStampTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

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


    }

    private fun googleLoginEvent() {
        googleLogin.googleLogin(this)
        googleLogin.loginEvent(this)
    }

    private fun kakaoLoginEvent() {

    }
}