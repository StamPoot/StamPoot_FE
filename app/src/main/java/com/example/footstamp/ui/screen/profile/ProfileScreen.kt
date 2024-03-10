package com.example.footstamp.ui.screen.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Divider
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.footstamp.R
import com.example.footstamp.ui.base.BaseScreen
import com.example.footstamp.ui.components.BodyText
import com.example.footstamp.ui.components.SpaceMaker
import com.example.footstamp.ui.components.TitleLargeText
import com.example.footstamp.ui.components.TopBar
import com.example.footstamp.ui.theme.MainColor
import com.example.footstamp.ui.theme.SubColor

@Composable
fun ProfileScreen() {
    BaseScreen { paddingValue ->
        val screenHeight = LocalConfiguration.current.screenHeightDp.dp
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBar(text = stringResource(R.string.screen_profile), backgroundColor = Color.White)
            ProfileCard(screenHeight = screenHeight)
            MyHistory(screenHeight = screenHeight)
        }
    }
}

@Composable
fun ProfileCard(screenHeight: Dp) {

    SpaceMaker(height = screenHeight / 20f)
    Card(modifier = Modifier.fillMaxWidth(0.9f)) {
        Column {
            Row {
                AsyncImage(model = R.drawable.icon_circle_small, contentDescription = null)
                TitleLargeText(text = "닉네임")
            }
            BodyText(text = "한 줄 인삿말")
        }
    }
    SpaceMaker(height = screenHeight / 20f)
}

@Composable
fun MyHistory(screenHeight: Dp) {
    Divider(color = SubColor)
    SpaceMaker(height = screenHeight / 40f)
    TitleLargeText(text = "나의 활동", modifier = Modifier.fillMaxWidth(), color = MainColor)
}