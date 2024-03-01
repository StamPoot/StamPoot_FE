package com.example.footstamp.ui.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.footstamp.ui.theme.BackColor
import com.example.footstamp.ui.theme.SubColor

@Composable
fun FullDialog(
    title: String = "",
    leftIcon: ImageVector = Icons.Default.ArrowBackIosNew,
    rightIcon: ImageVector? = null,
    onBackIconPressed: () -> Unit,
    onClickPressed: () -> Unit = {},
    screen: @Composable () -> Unit,
) {
    Dialog(
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        onDismissRequest = onBackIconPressed,
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .background(BackColor),
            shape = RectangleShape
        ) {
            TopBackBar(
                text = title,
                leftIcon = leftIcon,
                backgroundColor = Color.White,
                rightIcon = rightIcon,
                onBackPressed = onBackIconPressed,
                onClickPressed = onClickPressed
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
            TopBackBar(title, icon, onBackPressed = onChangeState)
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
                leftIcon = Icons.Default.Close,
                backgroundColor = Color.Black,
                iconColor = Color.White,
                onBackPressed = onClick
            )
            ZoomableImage(image = image)
        }
    }
}