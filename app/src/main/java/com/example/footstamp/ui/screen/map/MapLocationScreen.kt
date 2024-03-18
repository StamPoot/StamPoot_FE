package com.example.footstamp.ui.screen.map

import android.graphics.Bitmap
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRightAlt
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.footstamp.data.model.Diary
import com.example.footstamp.data.util.Formatter
import com.example.footstamp.data.util.SeoulLocation
import com.example.footstamp.ui.base.BaseScreen
import com.example.footstamp.ui.components.BodyText
import com.example.footstamp.ui.components.FullDialog
import com.example.footstamp.ui.components.ImagesLayout
import com.example.footstamp.ui.components.SpaceMaker
import com.example.footstamp.ui.components.TitleLargeText
import com.example.footstamp.ui.screen.gallery.GalleryReadScreen
import com.example.footstamp.ui.screen.gallery.GalleryViewModel
import com.example.footstamp.ui.theme.BackColor

@Composable
fun MapLocationScreen(mapScreenState: SeoulLocation, mapViewModel: MapViewModel = hiltViewModel()) {
    BaseScreen { paddingValue, screenWidth, screenHeight ->
        val diaries by mapViewModel.diaries.collectAsState()
        val readingDiary by mapViewModel.readingDiary.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackColor),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MapStaggeredView(
                diaries = diaries.filter { it.location == mapScreenState },
                paddingValues = paddingValue,
                onClickDiary = { mapViewModel.showReadScreen(diary = it) }
            )

            MapReadScreen(
                readingDiary = readingDiary, onChangeState = { mapViewModel.hideReadScreen() }
            )
        }
    }
}

@Composable
fun MapStaggeredView(
    diaries: List<Diary>,
    paddingValues: PaddingValues,
    onClickDiary: (diary: Diary) -> Unit
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = paddingValues,
        horizontalArrangement = Arrangement.SpaceAround,
        verticalItemSpacing = 10.dp,
    ) {
        items(diaries) { diary ->
            StaggeredViewItem(diary = diary, onClickBox = { onClickDiary(diary) })
        }
    }
}

@Composable
fun StaggeredViewItem(diary: Diary, onClickBox: (diary: Diary) -> Unit) {
    Box(modifier = Modifier
        .padding(5.dp)
        .clickable { onClickBox(diary) }) {
        Card(border = BorderStroke(5.dp, Color.White)) {
            AsyncImage(
                model = Formatter.convertStringToBitmap(diary.photoBitmapStrings[diary.thumbnail]),
                contentDescription = null
            )
        }
    }
}

@Composable
fun MapReadLayout(
    readingDiary: Diary,
    screenWidth: Dp,
    screenHeight: Dp,
    onClick: (Bitmap) -> Unit
) {
    SpaceMaker(screenHeight / 20)
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
        TitleLargeText(text = readingDiary.title, color = Color.Black)
    }
    SpaceMaker(screenHeight / 40)
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
            text = readingDiary.message, color = Color.Black, minLines = 8
        )
    }
}

@Composable
fun MapReadScreen(readingDiary: Diary?, onChangeState: () -> Unit) {
    if (readingDiary != null) {
        FullDialog(
            title = GalleryViewModel.WriteAndRead.READ.text,
            screen = { MapDetailScreen() },
            rightIcon = Icons.AutoMirrored.Filled.ArrowRightAlt,
            onBackIconPressed = onChangeState,
            onClickPressed = {}
        )
    }
}