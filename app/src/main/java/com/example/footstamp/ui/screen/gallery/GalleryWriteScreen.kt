package com.example.footstamp.ui.screen.gallery

import android.net.Uri
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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

    val itemWidth = LocalConfiguration.current.screenWidthDp.dp
    val itemHeight = LocalConfiguration.current.screenHeightDp.dp
    val writingDiary by galleryViewModel.writingDiary.collectAsState()
    val openingImage by galleryViewModel.openingImage.collectAsState()
    val dateOrLocationState by galleryViewModel.dateOrLocation.collectAsState()

    BaseScreen {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = itemWidth / 12),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            DateAndLocationWriteLayout(
                writingDiary = writingDiary,
                onClickDate = { galleryViewModel.showDateDialog() },
                onClickLocation = { galleryViewModel.showLocationDialog() })

            DiaryMainWriteLayout(
                writingDiary = writingDiary,
                itemHeight = itemHeight,
                onPhotoSelect = { galleryViewModel.openImageDetail(it) },
                onResetIndex = { galleryViewModel.updateWriteDiary(thumbnail = 0) }
            )
        }
        when (dateOrLocationState) {
            GalleryViewModel.DateAndLocation.DATE -> {
                HalfDialog(screen = {
                    DatePickerView(time = writingDiary.date, onChangeState = { time ->
                        galleryViewModel.updateWriteDiary(date = time)
                    }, onDismiss = { galleryViewModel.hideHalfDialog() })
                }) {}
            }

            GalleryViewModel.DateAndLocation.LOCATION -> {
                HalfDialog(screen = {
                    LocationPickerView { location ->
                        galleryViewModel.updateWriteDiary(location = location)
                        galleryViewModel.hideHalfDialog()
                    }
                }) {}
            }

            GalleryViewModel.DateAndLocation.NULL -> {}
        }
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
    itemHeight: Dp,
    onPhotoSelect: (Uri) -> Unit,
    onResetIndex: () -> Unit
) {
    SpaceMaker(itemHeight / 20)
    TextInput(hint = "제목")
    SpaceMaker(itemHeight / 40)
    PhotoSelector(
        maxSelectionCount = 5,
        onClickPhoto = onPhotoSelect,
        thumbnailIndex = writingDiary.thumbnail,
        onResetIndex = {
            onResetIndex()
        }

    )
    SpaceMaker(itemHeight / 40)
    TextInput(hint = "내용을 입력해주세요.", minLines = 5, maxLines = 10)
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