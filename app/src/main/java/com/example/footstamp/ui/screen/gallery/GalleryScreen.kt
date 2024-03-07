package com.example.footstamp.ui.screen.gallery

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRightAlt
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.footstamp.R
import com.example.footstamp.data.model.Diary
import com.example.footstamp.data.util.Formatter
import com.example.footstamp.ui.base.BaseScreen
import com.example.footstamp.ui.components.BodyLargeText
import com.example.footstamp.ui.components.FullDialog
import com.example.footstamp.ui.components.TitleLargeText
import com.example.footstamp.ui.components.TitleText
import com.example.footstamp.ui.components.TopBar
import com.example.footstamp.ui.components.TransparentButton
import com.example.footstamp.ui.theme.MainColor
import com.example.footstamp.ui.theme.SubColor

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun GalleryScreen(galleryViewModel: GalleryViewModel = hiltViewModel()) {

    val writeOrReadScreenState by galleryViewModel.writeOrRead.collectAsState()
    val sortType by galleryViewModel.sortType.collectAsState()

    BaseScreen(floatingButton = {
        GalleryFloatingButton {
            galleryViewModel.showWriteScreen()
        }
    }) { paddingValue ->
        val currentDiary by galleryViewModel.diaries.collectAsState()

        Column {
            TopBar(text = stringResource(R.string.screen_gallery), backgroundColor = Color.White)
            GallerySortLayout(
                sortType = sortType,
                onChangeSortState = { galleryViewModel.changeSortSwitch() })

            GalleryGridLayout(diaries = currentDiary,
                paddingValues = paddingValue,
                sortType = sortType,
                onClick = { galleryViewModel.showReadScreen(it) })
        }

        GalleryReadOrWriteScreen(
            writeOrReadScreenState = writeOrReadScreenState,
            onChangeState = { galleryViewModel.hideWriteOrReadScreen() },
            onClickWrite = {
                galleryViewModel.addDiary().also {
                    if (it) galleryViewModel.hideWriteOrReadScreen()
                }
            }
        )
    }
}

@Composable
fun GallerySortLayout(
    sortType: GalleryViewModel.SortByDateOrLocation,
    onChangeSortState: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BodyLargeText(text = sortType.text, color = MainColor)
        Switch(
            modifier = Modifier.padding(vertical = 10.dp),
            checked = sortType == GalleryViewModel.SortByDateOrLocation.DATE,
            onCheckedChange = { onChangeSortState() })
    }
}

@Composable
fun GalleryGridLayout(
    diaries: List<Diary>,
    paddingValues: PaddingValues,
    sortType: GalleryViewModel.SortByDateOrLocation,
    onClick: (Diary) -> Unit
) {
    val scrollState = rememberScrollState()
    val itemHeight = LocalConfiguration.current.screenHeightDp.dp

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
            .padding(paddingValues = paddingValues)
    ) {
        when (sortType) {
            GalleryViewModel.SortByDateOrLocation.DATE -> {
                diaries.sortedByDescending { it.date }
            }

            GalleryViewModel.SortByDateOrLocation.LOCATION -> {
                diaries.sortedBy { it.location }
            }
        }.forEach { diary ->
            GalleryItemView(diary = diary, itemHeight = itemHeight / 4, onClick = onClick)
        }
    }
}

@Composable
fun GalleryItemView(diary: Diary, itemHeight: Dp, onClick: (Diary) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(itemHeight)
            .background(SubColor)
    ) {
        AsyncImage(
            model = Formatter.convertStringToBitmap(diary.photoBitmapStrings[diary.thumbnail]),
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
        TransparentButton(onClick = { onClick(diary) })
    }
    HorizontalDivider(
        modifier = Modifier
            .height(1.dp)
            .background(SubColor)
    )
}

@Composable
fun GalleryReadOrWriteScreen(
    writeOrReadScreenState: GalleryViewModel.WriteAndRead,
    onChangeState: () -> Unit,
    onClickWrite: () -> Unit
) {
    when (writeOrReadScreenState) {
        GalleryViewModel.WriteAndRead.WRITE -> {
            FullDialog(
                title = GalleryViewModel.WriteAndRead.WRITE.text,
                screen = { GalleryWriteScreen() },
                rightIcon = Icons.AutoMirrored.Filled.ArrowRightAlt,
                onBackIconPressed = onChangeState,
                onClickPressed = onClickWrite
            )
        }

        GalleryViewModel.WriteAndRead.READ -> {
            FullDialog(
                title = GalleryViewModel.WriteAndRead.READ.text,
                screen = { GalleryReadScreen() },
                rightIcon = Icons.AutoMirrored.Filled.ArrowRightAlt,
                onBackIconPressed = onChangeState,
                onClickPressed = {}
            )
        }

        GalleryViewModel.WriteAndRead.NULL -> {}
    }
}

@Composable
fun GalleryFloatingButton(action: () -> Unit) {
    FloatingActionButton(
        modifier = Modifier, containerColor = MainColor, shape = CircleShape, onClick = action
    ) {
        Icon(
            painter = painterResource(R.drawable.icon_pen),
            contentDescription = null,
            tint = Color.White
        )
    }
}