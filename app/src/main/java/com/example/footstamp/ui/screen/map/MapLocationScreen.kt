package com.example.footstamp.ui.screen.map

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRightAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.footstamp.data.model.Diary
import com.example.footstamp.data.util.Formatter
import com.example.footstamp.data.util.SeoulLocation
import com.example.footstamp.ui.base.BaseScreen
import com.example.footstamp.ui.components.BodyText
import com.example.footstamp.ui.components.FullDialog
import com.example.footstamp.ui.components.SpaceMaker
import com.example.footstamp.ui.components.TitleText
import com.example.footstamp.ui.screen.gallery.GalleryViewModel
import com.example.footstamp.ui.theme.BackColor
import com.example.footstamp.ui.theme.SubColor

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
        Card(
            modifier = Modifier,
            border = BorderStroke(5.dp, Color.White),
            colors = CardColors(
                Color.White,
                Color.Transparent,
                Color.Transparent,
                Color.Transparent
            )
        ) {
            AsyncImage(
                model = Formatter.convertStringToBitmap(diary.photoBitmapStrings[diary.thumbnail]),
                contentDescription = null
            )
            SpaceMaker(height = 5.dp)
            TitleText(
                text = diary.title,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            SpaceMaker(height = 5.dp)
            BodyText(
                text = Formatter.dateToUserString(diary.date),
                color = SubColor,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            SpaceMaker(height = 5.dp)
        }
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