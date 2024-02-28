package com.example.footstamp.ui.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
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
                .background(BackColor),
            shape = RectangleShape
        ) {
            TopBackBar(
                text = title,
                icon = icon,
                onClick = onChangeState,
                backgroundColor = Color.White
            )
            screen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetDefaults(
    screen: @Composable () -> Unit,
    onChangeState: () -> Unit,
    title: String = "",
    icon: ImageVector = Icons.Default.ArrowBackIosNew
) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet = remember {
        mutableStateOf(false)
    }

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            showBottomSheet.value = false
        },
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

@Composable
fun ImageDialog(image: Uri, onClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopBackBar(
                text = "",
                icon = Icons.Default.Close,
                backgroundColor = Color.Black,
                iconColor = Color.White,
                onClick = onClick
            )
            ZoomableImage(image = image)
        }
    }
}