package com.example.footstamp.ui.screen.gallery

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.footstamp.data.util.Diary
import com.example.footstamp.ui.base.BaseScreen

@Composable
fun Screen() {

    BaseScreen(ViewModel()) {
//        GalleryGridLayout()
    }
}

@Composable
fun GalleryGridLayout(diaryList: List<Diary>) {
    Column {
        diaryList
    }
}