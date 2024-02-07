package com.example.footstamp.ui.screen.gallery

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.footstamp.ui.base.BaseScreen

@Composable
fun GalleryWriteScreen(galleryViewModel: GalleryViewModel = hiltViewModel()) {

    BaseScreen {
        Text(
            modifier = Modifier.fillMaxSize(), text = "햄스터"
        )
    }
}