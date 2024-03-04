package com.example.footstamp.ui.screen.map

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.footstamp.R
import com.example.footstamp.data.util.SeoulLocation
import com.example.footstamp.ui.base.BaseScreen
import com.example.footstamp.ui.components.FullDialog
import com.example.footstamp.ui.components.TitleLargeText
import com.example.footstamp.ui.components.TopBar
import com.example.footstamp.ui.components.TransparentButton
import com.example.footstamp.ui.theme.MainColor

@Composable
fun MapScreen(
    mapViewModel: MapViewModel = MapViewModel()
) {
    BaseScreen(containerColor = MainColor) { paddingValue ->
        val itemWidth = LocalConfiguration.current.screenWidthDp.dp
        val locationList = SeoulLocation.entries
        val mapScreenState by mapViewModel.screenMapState.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MainColor),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TopBar(text = stringResource(R.string.screen_map), backgroundColor = Color.White)
            Box(
                modifier = Modifier
                    .width(itemWidth)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Image(painter = painterResource(id = R.drawable.map_all), contentDescription = null)
                locationList.forEach { location ->
                    MapButton(
                        location = location,
                        itemWidth = itemWidth,
                        onClick = { mapViewModel.updateMapState(location) }
                    )
                }
            }
        }
        if (mapScreenState != null) {
            MapDetailScreenLayout(
                mapScreenState = mapScreenState!!,
                onDismiss = { mapViewModel.dismissMapState() }
            )
        }
    }
}

@Composable
fun MapButton(location: SeoulLocation, itemWidth: Dp, onClick: () -> Unit) {
    when (location) {
        SeoulLocation.CENTRAL -> {
            Box(modifier = Modifier.offset(itemWidth * -0.06f, itemWidth * 0.01f)) {
                TitleLargeText(text = location.location)
                TransparentButton(modifier = Modifier.size(itemWidth * 0.15f), onClick = onClick)
            }
        }

        SeoulLocation.EAST -> {
            Box(modifier = Modifier.offset(itemWidth * 0.23f, itemWidth * 0.03f)) {
                TitleLargeText(text = location.location)
                TransparentButton(modifier = Modifier.size(itemWidth * 0.15f), onClick = onClick)
            }
        }

        SeoulLocation.WEST -> {
            Box(modifier = Modifier.offset(itemWidth * -0.23f, itemWidth * 0.18f)) {
                TitleLargeText(text = location.location)
                TransparentButton(modifier = Modifier.size(itemWidth * 0.15f), onClick = onClick)
            }
        }

        SeoulLocation.SOUTH -> {
            Box(modifier = Modifier.offset(itemWidth * 0.16f, itemWidth * 0.26f)) {
                TitleLargeText(text = location.location)
                TransparentButton(modifier = Modifier.size(itemWidth * 0.15f), onClick = onClick)
            }
        }

        SeoulLocation.NORTH -> {
            Box(modifier = Modifier.offset(itemWidth * 0.16f, itemWidth * -0.18f)) {
                TitleLargeText(text = location.location)
                TransparentButton(modifier = Modifier.size(itemWidth * 0.15f), onClick = onClick)
            }
        }
    }
}

@Composable
fun MapDetailScreenLayout(
    mapScreenState: SeoulLocation,
    onDismiss: () -> Unit
) {
    FullDialog(
        title = mapScreenState.location,
        screen = { MapDetailScreen() },
        onBackIconPressed = onDismiss,
    )
}