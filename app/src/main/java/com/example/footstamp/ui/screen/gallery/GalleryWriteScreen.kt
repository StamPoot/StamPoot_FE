package com.example.footstamp.ui.screen.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.footstamp.ui.components.PhotoSelector
import com.example.footstamp.ui.components.SpaceMaker
import com.example.footstamp.ui.components.TextInput
import java.time.LocalDateTime

@Composable
fun GalleryWriteScreen(galleryViewModel: GalleryViewModel = hiltViewModel()) {

    val itemWidth = LocalConfiguration.current.screenWidthDp.dp
    val itemHeight = LocalConfiguration.current.screenHeightDp.dp

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
            ChangeButton(
                icon = Icons.Default.CalendarMonth,
                text = Formatter.dateToString(LocalDateTime.now()),
                onClick = { })
            Text(text = "Location") // Todo Location Selector
            TextInput(hint = "제목")
            SpaceMaker(itemHeight / 40)

            PhotoSelector(maxSelectionCount = 5)
            TextInput(hint = "내용을 입력해주세요.", maxLines = 10)
            AddButton(text = "글쓰기", onClick = {
                // Todo: 임시 글쓰기 수정
                galleryViewModel.addDiary(
                    Diary(
                        title = "제목",
                        date = LocalDateTime.now(),
                        message = "메시지",
                        isShared = false,
                        photoURLs = listOf(),
                        thumbnail = 0,
                        location = SeoulLocation.CENTRAL,
                        uid = ""
                    )
                )
            })
        }
    }
}