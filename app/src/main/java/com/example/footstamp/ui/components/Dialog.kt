package com.example.footstamp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.footstamp.ui.theme.BackColor

@Composable
fun FullDialog(screen: @Composable () -> Unit, onChangeState: () -> Unit) {
    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = onChangeState,
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .background(BackColor)
                .padding(16.dp)
        ) {
            screen()
        }
    }
}