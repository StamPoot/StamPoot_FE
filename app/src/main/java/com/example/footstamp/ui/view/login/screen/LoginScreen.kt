package com.example.footstamp.ui.view.login.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getString
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.footstamp.R
import com.example.footstamp.ui.base.BaseScreen
import com.example.footstamp.ui.components.CustomWebView
import com.example.footstamp.ui.components.ImageButton
import com.example.footstamp.ui.view.util.LoadingScreen
import com.example.footstamp.ui.components.SpaceMaker
import com.example.footstamp.ui.components.TitleLargeText
import com.example.footstamp.ui.view.login.LoginViewModel
import com.example.footstamp.ui.theme.MainColor
import com.example.footstamp.ui.theme.WhiteColor
import com.example.footstamp.ui.view.util.AlertScreen

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    onGoogleLogin: () -> Unit,
    onKakaoLogin: () -> Unit
) {
    val isKakaoLoginPress = loginViewModel.isKakaoLoginPress.collectAsState()
    val isLoading by loginViewModel.isLoading.collectAsState()
    val alert by loginViewModel.alertState.collectAsState()

    BaseScreen { paddingValue, screenWidth, screenHeight ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MainColor),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            SpaceMaker(height = 0.dp)
            AsyncImage(
                model = R.drawable.icon_circle_big,
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                TitleLargeText(text = "발도장", color = WhiteColor, fontSize = 40.sp)
                SpaceMaker(height = 20.dp)
                TitleLargeText(text = "로그인", color = WhiteColor)
            }
            Column {
                ImageButton(
                    image = R.drawable.icon_google_login,
                    buttonWidth = screenWidth / 2,
                    onClick = onGoogleLogin
                )
                SpaceMaker(height = 10.dp)
                ImageButton(image = R.drawable.icon_kakao_login,
                    buttonWidth = screenWidth / 2,
                    onClick = {
                        onKakaoLogin()
                        loginViewModel.pressKakaoLogin()
                    })
            }
            SpaceMaker(height = 0.dp)
        }
    }
    isLoading.let { if (it) LoadingScreen() }
    alert?.let { AlertScreen(alert = it) }
    if (isKakaoLoginPress.value) KakaoLoginWebView { loginViewModel.hideKakaoLogin() }
}

@Composable
fun KakaoLoginWebView(onResult: () -> Unit) {
    CustomWebView(
        url = getString(
            LocalContext.current,
            R.string.kakao_auth_url,
        ),
        onResult = onResult
    )
}