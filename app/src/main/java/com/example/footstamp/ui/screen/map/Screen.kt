package com.example.footstamp.ui.screen.map

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.footstamp.ui.base.BaseScreen

@Composable
fun Screen() {
    BaseScreen(ViewModel()) {
        Text(text = "Map")
    }
}