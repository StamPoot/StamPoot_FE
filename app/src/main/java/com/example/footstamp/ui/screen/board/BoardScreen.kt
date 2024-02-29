package com.example.footstamp.ui.screen.board

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.footstamp.R
import com.example.footstamp.ui.base.BaseScreen
import com.example.footstamp.ui.components.TopBar

@Composable
fun BoardScreen() {
    BaseScreen { paddingValue ->
        Column(modifier = Modifier.fillMaxSize()) {
            TopBar(text = stringResource(R.string.screen_board), backgroundColor = Color.White)
        }
    }
}