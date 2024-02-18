package com.example.footstamp.ui.screen.gallery

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.footstamp.R
import com.example.footstamp.data.model.Diary
import com.example.footstamp.data.util.Formatter
import com.example.footstamp.data.util.SeoulLocation
import com.example.footstamp.ui.base.BaseScreen
import com.example.footstamp.ui.components.FullDialog
import com.example.footstamp.ui.components.TitleLargeText
import com.example.footstamp.ui.components.TitleText
import com.example.footstamp.ui.theme.MainColor
import com.example.footstamp.ui.theme.SubColor
import java.time.LocalDateTime

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun GalleryScreen(galleryViewModel: GalleryViewModel = hiltViewModel()) {

    var declarationDialogState by remember {
        mutableStateOf(false)
    }

    BaseScreen(
        floatingButton = {
            GalleryFloatingButton { declarationDialogState = true }
        }) { paddingValue ->
        val currentDiary by galleryViewModel.diaries.collectAsState()

        val diaryList = listOf(
            Diary(
                title = "햄스터의 날",
                date = LocalDateTime.now(),
                message = "햄스터를 키웠던 날이에요",
                isShared = false,
                location = SeoulLocation.CENTRAL,
                photoURLs = listOf("https://i.namu.wiki/i/QQZ3wT3XWJ9FveqmsNKBXQz3O5S-AX3HxTs4ncjzTbUwmnJJlKQyMrQRlOlitttbylEWLViDSzIeRepZTfFj7dh_ZBJeh0Z0Rcr4G6EaeFDySQwUHoB5sPkp6PqOP9sA83-_4P1UyEwfW1s8TLcTQA.webp"),
                thumbnail = 0,
                uid = "a1234"
            ),
            Diary(
                title = "햄스터의 성장",
                date = LocalDateTime.now(),
                message = "햄스터가 성장한 날이에요",
                isShared = false,
                location = SeoulLocation.CENTRAL,
                photoURLs = listOf("https://i.namu.wiki/i/hLKYGp1WjQtZHr8ToxGT5uArhm_PIofVFdhytyMIAg_86n-Xxu-w0JzOpHYmw7abCxCqAdOP_NkTvTKUYE-Vb0edjhahGLCQ6tx0hsfg3LG3meX8t0_dDSkBGinhdctoZRaFIR2TE1rXHO6BIVu2Xw.webp"),
                thumbnail = 0,
                uid = "a1234"
            ),
            Diary(
                title = "햄스터의 햄스터",
                date = LocalDateTime.now(),
                message = "햄스터가 햄스터한 날이에요",
                isShared = false,
                location = SeoulLocation.CENTRAL,
                photoURLs = listOf("https://i.namu.wiki/i/OkhQuyObzv60hx9Mur4W_hQvvKrgf_mlBFYiSs_WVNjfV7-2LnLKSz_yf99XKQB1FxpzXbzymxqTEgr2JjO7jn6jHGn1U_FEcECV9ZE9Yyx-R65U_59o99aU-ozL2nuRsnJ8x6OyzoeaL6E473vd0w.webp"),
                thumbnail = 0,
                uid = "a1234"
            ),
        )

        GalleryGridLayout(diaries = diaryList, paddingValues = paddingValue)

        if (declarationDialogState) {
            FullDialog(
                title = "일기 작성",
                screen = { GalleryWriteScreen() },
                onChangeState = { declarationDialogState = false })
        }
    }
}


@Composable
fun GalleryGridLayout(diaries: List<Diary>, paddingValues: PaddingValues) {
    val scrollState = rememberScrollState()
    val itemHeight = LocalConfiguration.current.screenHeightDp.dp / 4

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
            .padding(paddingValues = paddingValues)
    ) {
        diaries.sortedBy { it.date }.forEach { diary ->
            GalleryItemView(diary = diary, itemHeight = itemHeight)
        }
    }
}

@Composable
fun GalleryItemView(diary: Diary, itemHeight: Dp) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(itemHeight)
            .background(SubColor)
    ) {
        AsyncImage(
            model = diary.photoURLs[diary.thumbnail],
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.6f),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleText(Formatter.dateToString(diary.date), Color.White)
            TitleLargeText(diary.title, Color.White)
        }
    }
    Divider(
        modifier = Modifier
            .height(1.dp)
            .background(SubColor)
    )
}

@Composable
fun GalleryFloatingButton(action: () -> Unit) {
    FloatingActionButton(
        modifier = Modifier,
        containerColor = MainColor,
        shape = CircleShape,
        onClick = action
    ) {
        Icon(
            painter = painterResource(R.drawable.icon_pen),
            contentDescription = null,
            tint = Color.White
        )
    }
}