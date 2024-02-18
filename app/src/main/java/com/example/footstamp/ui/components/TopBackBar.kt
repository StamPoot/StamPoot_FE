package com.example.footstamp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.footstamp.ui.theme.MainColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBackBar(text: String, icon: ImageVector, onClick: () -> Unit = {}) {

    val itemWidth = LocalConfiguration.current.screenWidthDp.dp

    TopAppBar(
        navigationIcon = {
            Button(
                onClick = onClick,
                modifier = Modifier
                    .background(Color.Transparent),
                colors = ButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = MainColor,
                    disabledContainerColor = Color.Transparent,
                    disabledContentColor = Color.Transparent
                )
            ) {
                Icon(icon, null)
            }
        },
        title = { TitleLargeText(text = text, MainColor, modifier = Modifier) },
    )
}