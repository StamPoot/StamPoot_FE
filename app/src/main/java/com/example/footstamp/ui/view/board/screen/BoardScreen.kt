package com.example.footstamp.ui.view.board.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.footstamp.R
import com.example.footstamp.data.model.Diary
import com.example.footstamp.data.repository.BoardSortType
import com.example.footstamp.data.util.Formatter
import com.example.footstamp.ui.base.BaseScreen
import com.example.footstamp.ui.components.BodyText
import com.example.footstamp.ui.components.FullDialog
import com.example.footstamp.ui.components.LabelText
import com.example.footstamp.ui.view.util.LoadingScreen
import com.example.footstamp.ui.components.SpaceMaker
import com.example.footstamp.ui.components.TopBar
import com.example.footstamp.ui.view.board.BoardViewModel
import com.example.footstamp.ui.view.gallery.GalleryViewModel
import com.example.footstamp.ui.theme.BlackColor
import com.example.footstamp.ui.theme.MainColor
import com.example.footstamp.ui.theme.SubColor
import com.example.footstamp.ui.theme.TransparentColor
import com.example.footstamp.ui.theme.WhiteColor
import com.example.footstamp.ui.view.util.AlertScreen

@Composable
fun BoardScreen(
    boardViewModel: BoardViewModel = hiltViewModel()
) {
    val isLoading by boardViewModel.isLoading.collectAsState()
    val alert by boardViewModel.alertState.collectAsState()

    BaseScreen { paddingValue, screenWidth, screenHeight ->
        val diaries by boardViewModel.diaries.collectAsState()
        val boardState by boardViewModel.boardState.collectAsState()
        val readingDiary by boardViewModel.readingDiary.collectAsState()

        Column(modifier = Modifier.fillMaxSize()) {
            TopBar(text = stringResource(R.string.screen_board),
                backgroundColor = WhiteColor,
                icon = when (boardState) {
                    BoardSortType.RECENT -> Icons.Default.AccessTime
                    BoardSortType.LIKE -> Icons.Default.Star
                },
                onClickPressed = { boardViewModel.changeBoardState() })
            BoardGridLayout(
                diaries = diaries,
                screenWidth = screenWidth,
                onClick = { boardViewModel.showReadScreen(it) })
            BoardReadScreen(readingDiary = readingDiary,
                onChangeState = { boardViewModel.hideReadScreen() })
        }
    }
    isLoading.let { if (it) LoadingScreen() }
    alert?.let { AlertScreen(alert = it) }
}

@Composable
fun BoardGridLayout(diaries: List<Diary>, screenWidth: Dp, onClick: (Diary) -> Unit) {
    LazyVerticalGrid(columns = GridCells.Fixed(3)) {
        items(diaries) { diary ->
            BoardGridItem(diary = diary, screenWidth = screenWidth, onClick = onClick)
        }
    }
}

@Composable
fun BoardGridItem(diary: Diary, screenWidth: Dp, onClick: (Diary) -> Unit) {
    Box(modifier = Modifier
        .padding(3.dp)
        .size(screenWidth / 3, screenWidth / 15 * 7)
        .clickable { onClick(diary) }) {
        Card(
            colors = CardColors(
                WhiteColor, TransparentColor, TransparentColor, TransparentColor
            ), border = BorderStroke(0.1.dp, SubColor)
        ) {
            AsyncImage(
                model = Formatter.convertStringToBitmap(diary.photoBitmapStrings[diary.thumbnail]),
                contentDescription = null,
                modifier = Modifier
                    .padding(10.dp)
                    .size(screenWidth / 3 - 5.dp, screenWidth / 15 * 5),
                contentScale = ContentScale.Crop
            )
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(0.8f)) {
                    BodyText(
                        text = diary.title,
                        color = BlackColor,
                        modifier = Modifier.padding(horizontal = 10.dp),
                        maxLines = 1
                    )
                    SpaceMaker(height = 3.dp)
                    LabelText(
                        text = diary.location.location,
                        color = SubColor,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                    SpaceMaker(height = 3.dp)
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(horizontal = 3.dp)
                        .weight(0.2f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = MainColor
                    )
                    LabelText(text = diary.likes.toString(), color = BlackColor)
                    SpaceMaker(height = 3.dp)
                }
            }
        }
    }
}

@Composable
fun BoardReadScreen(readingDiary: Diary?, onChangeState: () -> Unit) {
    if (readingDiary != null) {
        FullDialog(title = GalleryViewModel.GalleryScreenState.READ.text,
            screen = { BoardDetailScreen() },
            onBackIconPressed = onChangeState,
            onClickPressed = {})
    }
}