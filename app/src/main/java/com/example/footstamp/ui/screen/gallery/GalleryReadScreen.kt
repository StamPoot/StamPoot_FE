package com.example.footstamp.ui.screen.gallery

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.footstamp.data.util.Formatter
import com.example.footstamp.ui.base.BaseScreen
import com.example.footstamp.ui.components.AddButton
import com.example.footstamp.ui.components.BodyText
import com.example.footstamp.ui.components.ImageDialog
import com.example.footstamp.ui.components.ImagesLayout
import com.example.footstamp.ui.components.SpaceMaker
import com.example.footstamp.ui.components.TitleLargeText
import com.example.footstamp.ui.components.TitleText
import com.example.footstamp.ui.theme.BackColor
import com.example.footstamp.ui.theme.MainColor
import com.example.footstamp.ui.theme.SubColor

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
                .padding(horizontal = itemWidth / 12)
        ) {
            SpaceMaker(itemHeight / 40)
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                TitleText(
                    text = Formatter.dateToString(readingDiary.date),
                    color = MainColor,
                    textAlign = TextAlign.Start
                )
                TitleText(
                    text = "서울 ${readingDiary.location.location}에서",
                    color = MainColor,
                    textAlign = TextAlign.Start
                )
            }
            SpaceMaker(itemHeight / 20)
            TitleLargeText(text = readingDiary.title, color = MainColor)
            SpaceMaker(itemHeight / 20)
            ImagesLayout(
                selectedImages = readingDiary.photoURLs.map { Uri.parse(it) },
                onClick = { galleryViewModel.openImageDetail(it) }
            )
            SpaceMaker(itemHeight / 40)
            Box(
                modifier = Modifier
                    .background(BackColor)
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                BodyText(
                    text = readingDiary.message,
                    color = MainColor,
                    minLines = 8
                )
            }
            SpaceMaker(itemHeight / 20)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) { AddButton("수정하기") }
        }
        openingImage?.let { ImageDialog(image = it, onClick = { galleryViewModel.closeImage() }) }
    }
}