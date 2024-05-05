package com.example.footstamp.ui.screen.board

import android.graphics.Bitmap
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
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
import coil.compose.AsyncImage
import com.example.footstamp.R
import com.example.footstamp.data.model.Diary
import com.example.footstamp.data.util.Formatter
import com.example.footstamp.ui.base.BaseScreen
import com.example.footstamp.ui.components.BodyText
import com.example.footstamp.ui.components.ImageDialog
import com.example.footstamp.ui.components.ImagesLayout
import com.example.footstamp.ui.components.LabelText
import com.example.footstamp.ui.components.SpaceMaker
import com.example.footstamp.ui.components.TitleLargeText
import com.example.footstamp.ui.components.TitleText
import com.example.footstamp.ui.theme.BackColor
import com.example.footstamp.ui.theme.MainColor

@Composable
fun BoardDetailScreen(boardViewModel: BoardViewModel = hiltViewModel()) {
    val readingDiary by boardViewModel.readingDiary.collectAsState()
    val openingImage by boardViewModel.openingImage.collectAsState()

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
            WriterLayout(writer = "햄스터", screenWidth = screenWidth, screenHeight = screenHeight)
            BoardDateAndLocationLayout(screenHeight = screenHeight, readingDiary = readingDiary!!)
            BoardDetailReadLayout(readingDiary = readingDiary!!,
                screenWidth = screenWidth,
                screenHeight = screenHeight,
                onClick = { boardViewModel.openImageDetail(it) })
            BoardShareLayout(diary = readingDiary!!, onTapLike = { boardViewModel.likeDiary() })
        }
        // 사진 크게보기
        openingImage?.let { ImageDialog(image = it, onClick = { boardViewModel.closeImage() }) }
    }
}

@Composable
fun WriterLayout(writer: String, screenWidth: Dp, screenHeight: Dp) {
    Card(
        colors = CardColors(
            BackColor,
            Color.Transparent,
            Color.Transparent,
            Color.Transparent
        )
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = R.drawable.icon_circle_small,
                modifier = Modifier.size(screenWidth / 10, screenWidth / 10),
                contentDescription = null
            )
            SpaceMaker(width = 10.dp)
            BodyText(
                text = writer,
                color = Color.Black,
                textAlign = TextAlign.Start
            )
        }
    }
}


@Composable
fun BoardDateAndLocationLayout(screenHeight: Dp, readingDiary: Diary) {
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
fun BoardDetailReadLayout(
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
    SpaceMaker(screenHeight / 40)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        BodyText(
            text = readingDiary.message, color = Color.Black, minLines = 3
        )
    }
    SpaceMaker(height = 5.dp)
}

@Composable
fun BoardShareLayout(diary: Diary, onTapLike: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                modifier = Modifier.clickable { onTapLike() },
                contentDescription = null,
                tint = MainColor
            )
            SpaceMaker(width = 2.dp)
            LabelText(text = diary.likes.toString(), color = MainColor)
        }
    }

}