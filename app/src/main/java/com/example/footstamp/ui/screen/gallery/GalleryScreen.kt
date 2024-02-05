package com.example.footstamp.ui.screen.gallery

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.footstamp.data.model.Diary
import com.example.footstamp.ui.base.BaseScreen
import com.example.footstamp.ui.components.BodyLargeText
import com.example.footstamp.ui.components.BodyText
import com.example.footstamp.ui.components.LabelText
import com.example.footstamp.ui.theme.MainColor
import com.example.footstamp.ui.theme.SubColor

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun GalleryScreen(galleryViewModel: GalleryViewModel = hiltViewModel()) {

    BaseScreen(galleryViewModel) {
        val currentDiary by galleryViewModel.diaries.collectAsState()

        GalleryGridLayout(diaryList = currentDiary)
    }
}

@Composable
fun GalleryGridLayout(diaryList: List<Diary>) {

    Column {
        diaryList.forEach { diary ->
            BodyLargeText("title : ${diary.title}", MainColor)
            BodyText("message : ${diary.message}")
            Spacer(modifier = Modifier.fillMaxWidth().height(5.dp))
        }
    }
}