package com.example.footstamp.ui.screen.map

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.footstamp.ui.base.BaseScreen
import com.example.footstamp.ui.theme.MainColor

@Composable
fun MapDetailScreen(mapViewModel: MapViewModel = hiltViewModel()) {
    BaseScreen { paddingValue ->
        val mapState by mapViewModel.screenMapState.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MainColor),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            mapState?.let { painterResource(id = it.map) }?.let {
                Image(
                    painter = it,
                    contentDescription = null
                )
            }
        }
    }
}