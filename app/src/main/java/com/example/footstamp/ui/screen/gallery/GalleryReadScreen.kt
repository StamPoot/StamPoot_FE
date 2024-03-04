package com.example.footstamp.ui.screen.gallery

import android.net.Uri
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.footstamp.data.model.Diary
import com.example.footstamp.data.util.Formatter
import com.example.footstamp.ui.base.BaseScreen
import com.example.footstamp.ui.components.BodyText
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
    val itemWidth = LocalConfiguration.current.screenWidthDp.dp
    val itemHeight = LocalConfiguration.current.screenHeightDp.dp

    BaseScreen { paddingValue ->
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = itemWidth / 12),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            DateAndLocationReadLayout(itemHeight = itemHeight, readingDiary = readingDiary)
            DiaryMainReadLayout(
                itemHeight = itemHeight,
                readingDiary = readingDiary,
                onClick = { galleryViewModel.openImageDetail(it) }
            )
        }
        // 사진 크게보기
        openingImage?.let { ImageDialog(image = it, onClick = { galleryViewModel.closeImage() }) }
    }
}

@Composable
fun DateAndLocationReadLayout(itemHeight: Dp, readingDiary: Diary) {
    SpaceMaker(itemHeight / 40)
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
                text = Formatter.dateToString(readingDiary.date),
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
fun DiaryMainReadLayout(readingDiary: Diary, itemHeight: Dp, onClick: (Uri) -> Unit) {
    SpaceMaker(itemHeight / 20)
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
        TitleLargeText(text = readingDiary.title, color = Color.Black)
    }
    SpaceMaker(itemHeight / 40)
    ImagesLayout(
        selectedImages = readingDiary.photoURLs.map { Uri.parse(it) },
        onClickPhoto = onClick
    )
    SpaceMaker(itemHeight / 40)
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