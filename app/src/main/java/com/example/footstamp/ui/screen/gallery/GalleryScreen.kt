package com.example.footstamp.ui.screen.gallery

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.footstamp.data.model.Diary
import com.example.footstamp.ui.base.BaseScreen

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun GalleryScreen(galleryViewModel: GalleryViewModel = hiltViewModel()) {

    BaseScreen(galleryViewModel) {

        GalleryGridLayout(galleryViewModel.diaries.value)
    }
}

@Composable
fun GalleryGridLayout(diaryList: List<Diary>) {
    Column {

    }
}