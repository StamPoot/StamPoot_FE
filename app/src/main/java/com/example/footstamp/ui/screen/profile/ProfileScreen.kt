package com.example.footstamp.ui.screen.profile

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.footstamp.R
import com.example.footstamp.ui.base.BaseScreen
import com.example.footstamp.ui.components.AddButton
import com.example.footstamp.ui.components.BodyLargeText
import com.example.footstamp.ui.components.BodyText
import com.example.footstamp.ui.components.LabelText
import com.example.footstamp.ui.components.SpaceMaker
import com.example.footstamp.ui.components.TitleLargeText
import com.example.footstamp.ui.components.TopBar
import com.example.footstamp.ui.theme.MainColor
import com.example.footstamp.ui.theme.SubColor

@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel = hiltViewModel()) {

    val profileState by profileViewModel.profileState.collectAsState()

    BaseScreen { paddingValue, screenWidth, screenHeight ->
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBar(text = stringResource(R.string.screen_profile), backgroundColor = Color.White)
            ProfileCard(
                screenWidth = screenWidth,
                screenHeight = screenHeight,
                paddingValues = paddingValue
            )
            AddButton("수정하기")
            MyHistory(screenHeight = screenHeight, paddingValues = paddingValue)
        }
    }
}

@Composable
fun ProfileCard(screenWidth: Dp, screenHeight: Dp, paddingValues: PaddingValues) {

    SpaceMaker(height = screenHeight / 20f)
    Card(modifier = Modifier.fillMaxWidth(0.9f)) {
        Column(
            modifier = Modifier.padding(
                vertical = screenHeight / 40,
                horizontal = screenWidth / 40
            )
        ) {
            Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = R.drawable.icon_circle_small,
                    modifier = Modifier.size(screenWidth / 8, screenWidth / 8),
                    contentDescription = null
                )
                SpaceMaker(width = 20.dp)
                TitleLargeText(text = "햄스터", color = Color.Black)
            }
            SpaceMaker(height = 20.dp)
            BodyText(text = "안녕하세요, 서울을 사랑하는 햄스터입니다.", color = MainColor)
        }
    }
    SpaceMaker(height = screenHeight / 40f)
}

@Composable
fun MyHistory(screenHeight: Dp, paddingValues: PaddingValues) {
    SpaceMaker(height = screenHeight / 40f)
    TopBar(text = "나의 활동")
    Column(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .verticalScroll(rememberScrollState())
    ) {
        NotificationItem("햄스터님이 댓글을 남겼습니다.", "3분전", "저두 그래요")
        NotificationItem("피그님이 댓글을 남겼습니다.", "7분전", "정말 좋네요")
        NotificationItem("기린님이 댓글을 남겼습니다.", "10분전", "ㄹㅇㅋㅋ")
        NotificationItem("기린님이 댓글을 남겼습니다.", "10분전", "ㄹㅇㅋㅋ")
        NotificationItem("기린님이 댓글을 남겼습니다.", "10분전", "ㄹㅇㅋㅋ")
        NotificationItem("기린님이 댓글을 남겼습니다.", "10분전", "ㄹㅇㅋㅋ")
        NotificationItem("기린님이 댓글을 남겼습니다.", "10분전", "ㄹㅇㅋㅋ")
    }
}

@Composable
fun NotificationItem(text: String, time: String, message: String) {
    SpaceMaker(height = 5.dp)
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        SpaceMaker(height = 5.dp)
        BodyText(text = text, color = MainColor, modifier = Modifier.padding(horizontal = 5.dp))
        SpaceMaker(height = 5.dp)
        BodyLargeText(
            text = message,
            color = Color.Black,
            modifier = Modifier.padding(horizontal = 5.dp)
        )
        SpaceMaker(height = 5.dp)
        LabelText(text = time, color = SubColor, modifier = Modifier.padding(horizontal = 5.dp))
        SpaceMaker(height = 5.dp)
    }
    SpaceMaker(height = 5.dp)
}
