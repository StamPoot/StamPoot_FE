package com.example.footstamp.ui.screen.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.footstamp.R
import com.example.footstamp.ui.base.BaseScreen
import com.example.footstamp.ui.components.AddButton
import com.example.footstamp.ui.components.SpaceMaker
import com.example.footstamp.ui.components.TitleLargeText
import com.example.footstamp.ui.theme.MainColor

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    onGoogleLogin: () -> Unit,
    onKakaoLogin: () -> Unit
) {
    BaseScreen {  paddingValue, screenWidth, screenHeight ->
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
                TitleLargeText(text = "발도장", color = Color.White, fontSize = 40.sp)
                SpaceMaker(height = 20.dp)
                TitleLargeText(text = "로그인", color = Color.White)
            }
            Column {
                AddButton(text = "google", onClick = onGoogleLogin)
                AddButton("kakao")
            }
            SpaceMaker(height = 0.dp)
        }
    }
}