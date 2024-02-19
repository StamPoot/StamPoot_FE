package com.example.footstamp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.footstamp.ui.theme.BackColor

@Composable
fun FullDialog(
    screen: @Composable () -> Unit,
    onChangeState: () -> Unit,
    title: String = "",
    icon: ImageVector = Icons.Default.ArrowBackIosNew
) {
    Dialog(
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        onDismissRequest = onChangeState,
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .background(BackColor)
        ) {
            TopBackBar(title, icon, onClick = onChangeState)
            screen()
        }
    }
}

@Composable
fun HalfDialog(
    screen: @Composable () -> Unit,
    onChangeState: () -> Unit,
) {
    Dialog(
        onDismissRequest = onChangeState,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Card {
            screen()
        }
    }
}