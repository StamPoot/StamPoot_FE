package com.example.footstamp.ui.screen.map

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.footstamp.R
import com.example.footstamp.ui.base.BaseScreen
import com.example.footstamp.ui.components.TopBar
import com.example.footstamp.ui.theme.MainColor

@Composable
fun MapScreen(
    mapViewModel: MapViewModel = MapViewModel()
) {
    BaseScreen { paddingValue ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MainColor),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TopBar(text = stringResource(R.string.screen_map), backgroundColor = Color.White)
            Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
                Image(painter = painterResource(id = R.drawable.map_all), contentDescription = null)
            }
        }
    }
}