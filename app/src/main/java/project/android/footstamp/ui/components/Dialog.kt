package project.android.footstamp.ui.components

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import project.android.footstamp.R
import project.android.footstamp.ui.theme.BackColor
import project.android.footstamp.ui.theme.BlackColor
import project.android.footstamp.ui.theme.TransparentColor
import project.android.footstamp.ui.theme.WhiteColor

@Composable
fun FullDialog(
    title: String = "",
    leftIcon: ImageVector = Icons.Default.ArrowBackIosNew,
    rightIcon: ImageVector? = null,
    rightIconDrawable: Painter? = null,
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
                backgroundColor = WhiteColor,
                rightIcon = rightIcon,
                rightIconDrawable = rightIconDrawable,
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
    onChangeState: () -> Unit,
    screen: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onChangeState,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Card(
            colors = CardColors(
                WhiteColor,
                TransparentColor,
                TransparentColor,
                TransparentColor
            )
        ) {
            screen()
        }
    }
}

@Composable
fun ImageDialog(image: Bitmap, onClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .background(BlackColor)
            .fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopBackBar(
                text = stringResource(R.string.empty_string),
                leftIcon = Icons.Default.Close,
                backgroundColor = BlackColor,
                iconColor = WhiteColor,
                onBackPressed = onClick
            )
            ZoomableImage(image = image)
        }
    }
}