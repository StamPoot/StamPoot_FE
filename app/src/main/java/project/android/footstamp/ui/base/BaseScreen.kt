package project.android.footstamp.ui.base

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import project.android.footstamp.ui.theme.WhiteColor
import project.android.footstamp.ui.view.util.BackOnPressed

@Composable
fun BaseScreen(
    modifier: Modifier = Modifier,
    floatingButton: @Composable () -> Unit = {},
    containerColor: Color = WhiteColor,
    content: @Composable (paddingValue: PaddingValues, screenWidth: Dp, screenHeight: Dp) -> Unit,
) {
    BackOnPressed()
    Scaffold(
        modifier = modifier,
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = floatingButton,
        containerColor = containerColor
    ) { paddingValue ->
        val screenWidth = LocalConfiguration.current.screenWidthDp.dp
        val screenHeight = LocalConfiguration.current.screenHeightDp.dp
        content(paddingValue, screenWidth, screenHeight)
    }
}

