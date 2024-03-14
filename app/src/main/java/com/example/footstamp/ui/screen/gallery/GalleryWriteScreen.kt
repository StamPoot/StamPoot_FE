package com.example.footstamp.ui.screen.gallery

import android.content.ContentResolver
import android.graphics.Bitmap
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.footstamp.data.model.Diary
import com.example.footstamp.data.util.Formatter
import com.example.footstamp.data.util.SeoulLocation
import com.example.footstamp.ui.base.BaseScreen
import com.example.footstamp.ui.components.ChangeButton
import com.example.footstamp.ui.components.DatePickerView
import com.example.footstamp.ui.components.HalfDialog
import com.example.footstamp.ui.components.ImageDialog
import com.example.footstamp.ui.components.LocationPickerView
import com.example.footstamp.ui.components.PhotoSelector
import com.example.footstamp.ui.components.SpaceMaker
import com.example.footstamp.ui.components.TextInput
import com.example.footstamp.ui.theme.MainColor
import java.time.LocalDateTime

@Composable
fun GalleryWriteScreen(galleryViewModel: GalleryViewModel = hiltViewModel()) {
    val writingDiary by galleryViewModel.writingDiary.collectAsState()
    val openingImage by galleryViewModel.openingImage.collectAsState()
    val dateOrLocationState by galleryViewModel.dateOrLocation.collectAsState()

    BaseScreen { paddingValue, screenWidth, screenHeight ->
        val scrollState = rememberScrollState()
        val context = LocalContext.current
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = screenWidth / 12),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            DateAndLocationWriteLayout(
                writingDiary = writingDiary,
                onClickDate = { galleryViewModel.showDateDialog() },
                onClickLocation = { galleryViewModel.showLocationDialog() })

            DiaryMainWriteLayout(
                writingDiary = writingDiary,
                screenWidth = screenWidth,
                screenHeight = screenHeight,
                onPhotoIndexSelect = { galleryViewModel.updateWriteDiary(thumbnail = it) },
                onSetPhoto = { galleryViewModel.updateWriteDiary(photoURLs = it, thumbnail = 0) },
                onTitleFieldChange = { galleryViewModel.updateWriteDiary(title = it) },
                contentResolver = context.contentResolver,
                onMessageFieldChange = { galleryViewModel.updateWriteDiary(message = it) }
            )
        }
        DateAndLocationDialogLayout(
            dateOrLocationState = dateOrLocationState,
            writingDiary = writingDiary,
            onClickDate = { time ->
                galleryViewModel.updateWriteDiary(date = time)
            },
            onClickLocation = { location ->
                galleryViewModel.updateWriteDiary(location = location)
                galleryViewModel.hideHalfDialog()
            },
            onDismiss = { galleryViewModel.hideHalfDialog() }
        )

        openingImage?.let { ImageDialog(image = it, onClick = { galleryViewModel.closeImage() }) }
    }
}

@Composable
fun DateAndLocationWriteLayout(
    writingDiary: Diary, onClickDate: () -> Unit, onClickLocation: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
    ) {
        CalendarChangeButton(writingDiary.date) {
            onClickDate()
        }
        LocationChangeButton(writingDiary.location) {
            onClickLocation()
        }
    }
}

@Composable
fun DiaryMainWriteLayout(
    writingDiary: Diary,
    screenWidth: Dp,
    screenHeight: Dp,
    contentResolver: ContentResolver,
    onPhotoSelect: (Bitmap) -> Unit = {},
    onPhotoIndexSelect: (Int) -> Unit = {},
    onSetPhoto: (List<String>) -> Unit,
    onTitleFieldChange: (text: String) -> Unit,
    onMessageFieldChange: (text: String) -> Unit,
) {
    SpaceMaker(screenHeight / 20)
    TextInput(hint = "제목", onValueChange = onTitleFieldChange)
    SpaceMaker(screenHeight / 40)
    PhotoSelector(
        maxSelectionCount = 5,
        screenWidth = screenWidth,
        screenHeight = screenHeight,
        onClickPhoto = onPhotoSelect,
        onClickPhotoIndex = onPhotoIndexSelect,
        thumbnailIndex = writingDiary.thumbnail,
        contentResolver = contentResolver,
        onSetPhoto = onSetPhoto,
    )
    SpaceMaker(screenHeight / 40)
    TextInput(hint = "내용을 입력하세요", minLines = 5, maxLines = 10, onValueChange = onMessageFieldChange)
}

@Composable
fun DateAndLocationDialogLayout(
    dateOrLocationState: GalleryViewModel.DateAndLocation,
    writingDiary: Diary,
    onClickDate: (LocalDateTime) -> Unit,
    onClickLocation: (SeoulLocation) -> Unit,
    onDismiss: () -> Unit
) {
    when (dateOrLocationState) {
        GalleryViewModel.DateAndLocation.DATE -> {
            HalfDialog(onChangeState = {}) {
                DatePickerView(
                    time = writingDiary.date,
                    onChangeState = onClickDate,
                    onDismiss = onDismiss
                )
            }
        }

        GalleryViewModel.DateAndLocation.LOCATION -> {
            HalfDialog(onChangeState = {}) {
                LocationPickerView(onClickLocation)
            }
        }

        GalleryViewModel.DateAndLocation.NULL -> {}
    }
}

@Composable
fun CalendarChangeButton(time: LocalDateTime, onClick: () -> Unit) {
    ChangeButton(
        icon = Icons.Default.CalendarMonth,
        text = Formatter.dateToString(time),
        color = Color.Transparent,
        tint = MainColor,
        underLine = true,
        onClick = onClick
    )
}

@Composable
fun LocationChangeButton(location: SeoulLocation, onClick: () -> Unit) {
    ChangeButton(
        icon = Icons.Default.PinDrop,
        text = location.location,
        color = Color.Transparent,
        tint = MainColor,
        underLine = true,
        onClick = onClick
    )
}