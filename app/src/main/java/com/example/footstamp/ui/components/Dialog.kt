package com.example.footstamp.ui.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
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
                .background(BackColor)
        ) {
            TopBackBar(title, icon, onClick = onChangeState)
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
fun ImageDialog(image: Uri) {
    Box(modifier = Modifier
        .background(Color.Black)
        .fillMaxSize()
    ) {
        AsyncImage(model = image, contentDescription = null)
    }
}