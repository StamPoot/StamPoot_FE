package com.example.footstamp.ui.screen.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.footstamp.data.model.Diary
import com.example.footstamp.data.util.Formatter
import com.example.footstamp.data.util.SeoulLocation
import com.example.footstamp.ui.base.BaseScreen
import com.example.footstamp.ui.components.AddButton
import com.example.footstamp.ui.components.ChangeButton
import com.example.footstamp.ui.components.DatePickerView
import com.example.footstamp.ui.components.HalfDialog
import com.example.footstamp.ui.components.LocationPickerView
import com.example.footstamp.ui.components.PhotoSelector
import com.example.footstamp.ui.components.SpaceMaker
import com.example.footstamp.ui.components.TextInput
import java.time.LocalDateTime

@Composable
fun GalleryWriteScreen(galleryViewModel: GalleryViewModel = hiltViewModel()) {

    val itemWidth = LocalConfiguration.current.screenWidthDp.dp
    val itemHeight = LocalConfiguration.current.screenHeightDp.dp
    val isShowHalfDialog by galleryViewModel.isShowHalfDialog.collectAsState()
    val writingDiary by galleryViewModel.writingDiary.collectAsState()
    val dateOrLocation by galleryViewModel.dateOrLocation.collectAsState()

    BaseScreen {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = itemWidth / 12),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            CalendarChangeButton(writingDiary.date) {
                galleryViewModel.changeToDate()
                galleryViewModel.showHalfDialog()
            }
            LocationChangeButton(writingDiary.location) {
                galleryViewModel.changeToLocation()
                galleryViewModel.showHalfDialog()
            }
            TextInput(hint = "제목")
            SpaceMaker(itemHeight / 40)

            PhotoSelector(maxSelectionCount = 5)
            TextInput(hint = "내용을 입력해주세요.", maxLines = 10)
            AddButton(text = "글쓰기", onClick = {
                galleryViewModel.addDiary()
                galleryViewModel.hideWriteScreen()
            })
        }
        if (isShowHalfDialog) HalfDialog(
            screen = {
                if (dateOrLocation == GalleryViewModel.DateAndLocation.DATE) {
                    DatePickerView(time = writingDiary.date) { time ->
                        galleryViewModel.updateWriteDiary(date = time)
                        galleryViewModel.hideHalfDialog()
                    }
                } else {
                    LocationPickerView { location ->
                        galleryViewModel.updateWriteDiary(location = location)
                        galleryViewModel.hideHalfDialog()
                    }
                }
            },
            onChangeState = {}
        )
    }
}

@Composable
fun CalendarChangeButton(time: LocalDateTime, onClick: () -> Unit) {
    ChangeButton(
        icon = Icons.Default.CalendarMonth,
        text = Formatter.dateToString(time),
        onClick = onClick
    )
}

@Composable
fun LocationChangeButton(location: SeoulLocation, onClick: () -> Unit) {
    ChangeButton(
        icon = Icons.Default.LocationOn,
        text = location.location,
        onClick = onClick
    )
}