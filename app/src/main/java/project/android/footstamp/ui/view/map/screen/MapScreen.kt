package project.android.footstamp.ui.view.map.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import project.android.footstamp.R
import project.android.footstamp.data.util.SeoulLocation
import project.android.footstamp.ui.base.BaseScreen
import project.android.footstamp.ui.components.FullDialog
import project.android.footstamp.ui.components.TitleLargeText
import project.android.footstamp.ui.components.TopBar
import project.android.footstamp.ui.components.TransparentButton
import project.android.footstamp.ui.theme.MainColor
import project.android.footstamp.ui.theme.SubColor
import project.android.footstamp.ui.theme.WhiteColor
import project.android.footstamp.ui.view.map.MapViewModel
import project.android.footstamp.ui.view.util.AlertScreen
import project.android.footstamp.ui.view.util.LoadingScreen

@Composable
fun MapScreen(
    mapViewModel: MapViewModel = hiltViewModel()
) {
    val isLoading by mapViewModel.isLoading.collectAsState()
    val alert by mapViewModel.alertState.collectAsState()

    BaseScreen(containerColor = MainColor) { paddingValue, screenWidth, screenHeight ->
        val diaries by mapViewModel.diaries.collectAsState()
        val mapScreenState by mapViewModel.screenMapState.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MainColor),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TopBar(text = stringResource(R.string.screen_map), backgroundColor = WhiteColor)
            Box(
                modifier = Modifier
                    .width(screenWidth)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Image(painter = painterResource(id = R.drawable.map_all), contentDescription = null)
                SeoulLocation.entries.forEach { location ->
                    MapEntries(
                        location = location,
                        screenWidth = screenWidth,
                        count = diaries.count { it.location == location },
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
    isLoading.let { if (it) LoadingScreen() }
    alert?.let { AlertScreen(alert = it) }
}

@Composable
fun MapEntries(location: SeoulLocation, screenWidth: Dp, onClick: () -> Unit, count: Int) {
    when (location) {
        SeoulLocation.CENTRAL -> {
            LocationTextCountLayout(
                text = location.location,
                count = count,
                screenWidth = screenWidth,
                offsetX = -0.06f,
                offsetY = 0.03f,
                onClick = onClick
            )
        }

        SeoulLocation.EAST -> {
            LocationTextCountLayout(
                text = location.location,
                count = count,
                screenWidth = screenWidth,
                offsetX = 0.21f,
                offsetY = 0.04f,
                onClick = onClick
            )
        }

        SeoulLocation.WEST -> {
            LocationTextCountLayout(
                text = location.location,
                count = count,
                screenWidth = screenWidth,
                offsetX = -0.25f,
                offsetY = 0.18f,
                onClick = onClick
            )
        }

        SeoulLocation.SOUTH -> {
            LocationTextCountLayout(
                text = location.location,
                count = count,
                screenWidth = screenWidth,
                offsetX = 0.16f,
                offsetY = 0.26f,
                onClick = onClick
            )
        }

        SeoulLocation.NORTH -> {
            LocationTextCountLayout(
                text = location.location,
                count = count,
                screenWidth = screenWidth,
                offsetX = 0.16f,
                offsetY = -0.18f,
                onClick = onClick
            )
        }
    }
}

@Composable
fun LocationTextCountLayout(
    text: String,
    count: Int,
    screenWidth: Dp,
    offsetX: Float,
    offsetY: Float,
    onClick: () -> Unit,
) {
    Box(modifier = Modifier.offset(screenWidth * offsetX, screenWidth * offsetY)) {
        Row {
            TitleLargeText(text = "$text $count", color = if (count == 0) SubColor else MainColor)
        }
        TransparentButton(modifier = Modifier.size(screenWidth * 0.15f), onClick = onClick)
    }
}

@Composable
fun MapDetailScreenLayout(
    mapScreenState: SeoulLocation,
    onDismiss: () -> Unit
) {
    FullDialog(
        title = mapScreenState.location,
        screen = { MapLocationScreen(mapScreenState) },
        onBackIconPressed = onDismiss,
    )
}