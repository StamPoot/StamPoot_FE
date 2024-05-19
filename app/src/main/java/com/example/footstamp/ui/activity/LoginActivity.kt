package com.example.footstamp.ui.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.example.footstamp.data.login.GoogleLogin
import com.example.footstamp.data.login.KakaoLogin
import com.example.footstamp.ui.view.login.screen.LoginScreen
import com.example.footstamp.ui.theme.FootStampTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    private val loginViewModel by viewModels<LoginViewModel>()
    private lateinit var googleLogin: GoogleLogin
    private lateinit var kakaoLogin: KakaoLogin

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
            loginViewModel.loginToken.collect { loginToken ->
                if (loginToken != null) moveToHomeScreen()
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // 구글 로그인 get code
        if (requestCode == GOOGLE_API_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            googleLogin.handleSignInResult(task)?.let { token ->
                loginViewModel.updateGoogleIdToken(token)
                loginViewModel.googleAccessTokenLogin()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    private fun googleLoginEvent() {
        googleLogin = GoogleLogin(this)
        val signInIntent = googleLogin.signInIntent
        startActivityForResult(signInIntent, GOOGLE_API_CODE)
    }

    private fun kakaoLoginEvent() {
        kakaoLogin = KakaoLogin(this) { token ->
            loginViewModel.kakaoAccessTokenLogin(token)
        }
        if (kakaoLogin.checkKakaoLogin()) {
//            kakaoLogin.kakaoAppLogin()
            loginViewModel.kakaoLogin()
        } else {
            kakaoLogin.kakaoAccountLogin()
        }
    }

    private fun moveToHomeScreen() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    companion object {
        const val GOOGLE_API_CODE = 1001
        const val KAKAO_API_CODE = 1002
    }
}