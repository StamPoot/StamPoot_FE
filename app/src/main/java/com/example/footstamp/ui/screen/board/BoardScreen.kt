package com.example.footstamp.ui.screen.board

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.footstamp.ui.base.BaseScreen

@Composable
fun BoardScreen() {
    BaseScreen(BoardViewModel()) {
        Text(text = "Board")
    }
}