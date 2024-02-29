package com.example.footstamp.ui.screen.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.footstamp.R
import com.example.footstamp.ui.base.BaseScreen
import com.example.footstamp.ui.components.TopBar
import com.example.footstamp.ui.screen.map.MapViewModel

@Composable
fun ProfileScreen() {
    BaseScreen { paddingValue ->
        Column(
            Modifier.fillMaxSize(),
        ) {
            TopBar(text = stringResource(R.string.screen_profile), backgroundColor = Color.White)
        }
    }
}