package com.example.footstamp.ui.screen.gallery

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.footstamp.data.model.Diary
import com.example.footstamp.data.util.Formatter
import com.example.footstamp.ui.base.BaseScreen
import com.example.footstamp.ui.components.BodyText
import com.example.footstamp.ui.components.CommonButton
import com.example.footstamp.ui.components.HalfDialog
import com.example.footstamp.ui.components.ImageDialog
import com.example.footstamp.ui.components.ImagesLayout
import com.example.footstamp.ui.components.SpaceMaker
import com.example.footstamp.ui.components.TitleLargeText
import com.example.footstamp.ui.components.TitleText
import com.example.footstamp.ui.theme.MainColor

@Composable
fun GalleryReadScreen(galleryViewModel: GalleryViewModel = hiltViewModel()) {
    val readingDiary by galleryViewModel.readingDiary.collectAsState()
    val openingImage by galleryViewModel.openingImage.collectAsState()
    val isShowDiaryMenu by galleryViewModel.isShowDiaryMenu.collectAsState()

    BaseScreen { paddingValue, screenWidth, screenHeight ->
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = screenWidth / 12),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            DateAndLocationReadLayout(screenHeight = screenHeight, readingDiary = readingDiary)
            DiaryMainReadLayout(readingDiary = readingDiary,
                screenWidth = screenWidth,
                screenHeight = screenHeight,
                onClick = { galleryViewModel.openImageDetail(it) })
        }
        // 사진 크게보기
        openingImage?.let { ImageDialog(image = it, onClick = { galleryViewModel.closeImage() }) }
        if (isShowDiaryMenu) {
            GalleryDiaryMenu(
                screenHeight = screenHeight,
                onClickEdit = { galleryViewModel.showEditScreen() },
                onClickShare = {
                    //TODO: 일기 공유
                },
                onChangeState = { galleryViewModel.initializeViewState() }
            )
        }
    }
}

@Composable
fun DateAndLocationReadLayout(screenHeight: Dp, readingDiary: Diary) {
    SpaceMaker(height = screenHeight / 40)
    Row(
        horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.CalendarMonth,
                contentDescription = null,
                tint = MainColor
            )
            TitleText(
                text = Formatter.dateToUserString(readingDiary.date),
                color = MainColor,
                textAlign = TextAlign.Start
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.PinDrop, contentDescription = null, tint = MainColor)
            TitleText(
                text = readingDiary.location.location,
                color = MainColor,
                textAlign = TextAlign.Start
            )
        }
    }
}

@Composable
fun DiaryMainReadLayout(
    readingDiary: Diary, screenWidth: Dp, screenHeight: Dp, onClick: (Bitmap) -> Unit
) {
    SpaceMaker(height = screenHeight / 20)
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
        TitleLargeText(text = readingDiary.title, color = Color.Black)
    }
    SpaceMaker(height = screenHeight / 40)
    ImagesLayout(
        selectedImages = readingDiary.photoBitmapStrings.map { Formatter.convertStringToBitmap(it) },
        screenWidth = screenWidth,
        screenHeight = screenHeight,
        onClickPhoto = onClick
    )
    SpaceMaker(height = screenHeight / 40)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        BodyText(
            text = readingDiary.message, color = Color.Black, minLines = 8
        )
    }
}

@Composable
fun GalleryDiaryMenu(
    screenHeight: Dp,
    onClickEdit: () -> Unit,
    onClickShare: () -> Unit,
    onChangeState: () -> Unit
) {
    HalfDialog(onChangeState = onChangeState) {
        BodyText(text = "일기 메뉴", color = MainColor)
        SpaceMaker(height = screenHeight / 40)
        CommonButton(text = "일기 수정하기") { onClickEdit() }
        CommonButton(text = "게시판에 공유하기") { onClickShare() }
    }
}