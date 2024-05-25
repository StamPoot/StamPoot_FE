package com.example.footstamp.ui.view.login.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.footstamp.R
import com.example.footstamp.ui.base.BaseScreen
import com.example.footstamp.ui.components.CustomWebView
import com.example.footstamp.ui.components.ImageButton
import com.example.footstamp.ui.components.SpaceMaker
import com.example.footstamp.ui.components.TitleLargeText
import com.example.footstamp.ui.theme.BlackColor
import com.example.footstamp.ui.theme.MainColor
import com.example.footstamp.ui.theme.WhiteColor
import com.example.footstamp.ui.view.login.LoginViewModel
import com.example.footstamp.ui.view.util.AlertScreen
import com.example.footstamp.ui.view.util.LoadingScreen

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    kakaoLoginUrl: String,
    onGoogleLogin: () -> Unit,
    onKakaoLogin: (String) -> Unit
) {
    val isShowWebView by loginViewModel.isShowWebView.collectAsState()
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
                TitleLargeText(
                    text = stringResource(R.string.app_name),
                    color = WhiteColor,
                    fontSize = 40.sp
                )
                SpaceMaker(height = 20.dp)
                TitleLargeText(text = stringResource(R.string.login_login), color = WhiteColor)
            }
            Column {
                ImageButton(
                    image = R.drawable.icon_google_login,
                    buttonWidth = screenWidth / 2,
                    onClick = { if (!isShowWebView) onGoogleLogin() }
                )
                SpaceMaker(height = 10.dp)
//                ImageButton(
//                    image = R.drawable.icon_kakao_login,
//                    buttonWidth = screenWidth / 2,
//                    onClick = { if (!isShowWebView) loginViewModel.showKakaoLogin() }
//                )
            }
            SpaceMaker(height = 0.dp)
        }
    }
    isLoading.let { if (it) LoadingScreen() }
    alert?.let { AlertScreen(alert = it) }
    if (isShowWebView) KakaoLoginWebView(
        kakaoLoginUrl = kakaoLoginUrl,
        onCloseWebView = { loginViewModel.hideKakaoLogin() },
        onAuthCodeReceived = { code ->
            loginViewModel.hideKakaoLogin()
            onKakaoLogin(code)
        }
    )
}

@Composable
fun KakaoLoginWebView(
    kakaoLoginUrl: String,
    onCloseWebView: () -> Unit,
    onAuthCodeReceived: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WhiteColor)
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = null,
            tint = BlackColor,
            modifier = Modifier
                .fillMaxHeight(0.1f)
                .padding(horizontal = 10.dp)
                .clickable { onCloseWebView() }
        )
        CustomWebView(
            url = kakaoLoginUrl,
            onResult = { code -> onAuthCodeReceived(code) }
        )
    }
}