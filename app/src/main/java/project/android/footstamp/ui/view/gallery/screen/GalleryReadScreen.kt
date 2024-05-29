package project.android.footstamp.ui.view.gallery.screen

import android.graphics.Bitmap
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import project.android.footstamp.R
import project.android.footstamp.data.model.Diary
import project.android.footstamp.data.util.Formatter
import project.android.footstamp.ui.base.BaseScreen
import project.android.footstamp.ui.components.BodyText
import project.android.footstamp.ui.components.CommonButton
import project.android.footstamp.ui.components.ImageDialog
import project.android.footstamp.ui.components.ImagesLayout
import project.android.footstamp.ui.components.SpaceMaker
import project.android.footstamp.ui.components.TitleLargeText
import project.android.footstamp.ui.components.TitleText
import project.android.footstamp.ui.view.gallery.GalleryViewModel
import project.android.footstamp.ui.theme.BlackColor
import project.android.footstamp.ui.theme.MainColor
import project.android.footstamp.ui.theme.SubColor
import project.android.footstamp.ui.theme.WarnColor

@Composable
fun GalleryReadScreen(galleryViewModel: GalleryViewModel = hiltViewModel()) {

    BaseScreen { paddingValue, screenWidth, screenHeight ->
        val scrollState = rememberScrollState()
        val readingDiary by galleryViewModel.readingDiary.collectAsState()
        val openingImage by galleryViewModel.openingImage.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = screenWidth / 12),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            DateAndLocationReadLayout(screenHeight = screenHeight, readingDiary = readingDiary)
            DiaryMainReadLayout(
                readingDiary = readingDiary,
                screenWidth = screenWidth,
                screenHeight = screenHeight,
                onClick = { galleryViewModel.openImageDetail(it) },
                onShare = { galleryViewModel.shareTransDiaryAlert() }
            )
            DiaryDeleteLayout { galleryViewModel.deleteDiaryAlert() }
        }

        // 사진 크게보기
        openingImage?.let { ImageDialog(image = it, onClick = { galleryViewModel.closeImage() }) }
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
                text = Formatter.dateTimeToString(readingDiary.date),
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
    readingDiary: Diary, screenWidth: Dp, screenHeight: Dp, onClick: (Bitmap) -> Unit,
    onShare: () -> Unit
) {
    SpaceMaker(height = screenHeight / 20)
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        TitleLargeText(text = readingDiary.title, color = BlackColor)
        Icon(
            imageVector = Icons.Default.PushPin,
            contentDescription = null,
            tint = if (readingDiary.isShared) MainColor else SubColor,
            modifier = Modifier.clickable { onShare() }
        )
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
            text = readingDiary.message, color = BlackColor, minLines = 8
        )
    }
}

@Composable
fun DiaryDeleteLayout(onDeleteDiary: () -> Unit) {
    CommonButton(
        text = stringResource(R.string.gallery_delete),
        buttonColor = WarnColor
    ) { onDeleteDiary() }
}