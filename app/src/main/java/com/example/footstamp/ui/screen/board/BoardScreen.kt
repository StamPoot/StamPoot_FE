package com.example.footstamp.ui.screen.board

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRightAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.footstamp.R
import com.example.footstamp.data.model.Diary
import com.example.footstamp.data.util.Formatter
import com.example.footstamp.ui.base.BaseScreen
import com.example.footstamp.ui.components.BodyText
import com.example.footstamp.ui.components.FullDialog
import com.example.footstamp.ui.components.LabelText
import com.example.footstamp.ui.components.SpaceMaker
import com.example.footstamp.ui.components.TopBar
import com.example.footstamp.ui.screen.gallery.GalleryViewModel
import com.example.footstamp.ui.theme.SubColor

@Composable
fun BoardScreen(boardViewModel: BoardViewModel = hiltViewModel()) {
    BaseScreen { paddingValue, screenWidth, screenHeight ->
        val diaries by boardViewModel.diaries.collectAsState()
        val readingDiary by boardViewModel.readingDiary.collectAsState()

        Column(modifier = Modifier.fillMaxSize()) {
            TopBar(text = stringResource(R.string.screen_board), backgroundColor = Color.White)

            BoardGridLayout(diaries = diaries, onClick = { boardViewModel.showReadScreen(it) })

            BoardReadScreen(
                readingDiary = readingDiary,
                onChangeState = { boardViewModel.hideReadScreen() })
        }
    }
}

@Composable
fun BoardGridLayout(diaries: List<Diary>, onClick: (Diary) -> Unit) {
    LazyVerticalGrid(columns = GridCells.Fixed(3)) {
        items(diaries) { diary ->
            BoardGridItem(diary = diary, onClick = onClick)
        }
    }
}

@Composable
fun BoardGridItem(diary: Diary, onClick: (Diary) -> Unit) {
    Box(modifier = Modifier
        .padding(3.dp)
        .clickable { onClick(diary) }) {
        Card(
            colors = CardColors(
                Color.White,
                Color.Transparent,
                Color.Transparent,
                Color.Transparent
            ),
            border = BorderStroke(0.1.dp, SubColor)
        ) {
            AsyncImage(
                model = Formatter.convertStringToBitmap(diary.photoBitmapStrings[diary.thumbnail]),
                contentDescription = null,
                modifier = Modifier.padding(10.dp)
            )
            BodyText(
                text = diary.title, color = Color.Black,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            SpaceMaker(height = 3.dp)
            LabelText(
                text = diary.location.location, color = SubColor,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            SpaceMaker(height = 3.dp)
        }
    }
}

@Composable
fun BoardReadScreen(readingDiary: Diary?, onChangeState: () -> Unit) {
    if (readingDiary != null) {
        FullDialog(
            title = GalleryViewModel.GalleryScreenState.READ.text,
            screen = { BoardDetailScreen() },
            rightIcon = Icons.AutoMirrored.Filled.ArrowRightAlt,
            onBackIconPressed = onChangeState,
            onClickPressed = {}
        )
    }
}