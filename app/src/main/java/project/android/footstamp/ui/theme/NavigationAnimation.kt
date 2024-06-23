package project.android.footstamp.ui.theme

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.animatedComposable(
    enterAnimation: EnterTransition,
    exitAnimation: ExitTransition,
    route: String,
    content: @Composable () -> Unit
) {
    composable(route) { backStackEntry ->
        val visibleState = remember { mutableStateOf(true) }
        AnimatedVisibility(
            visible = visibleState.value,
            enter = enterAnimation,
            exit = exitAnimation
        ) {
            content()
        }
        LaunchedEffect(backStackEntry) {
            visibleState.value = true
        }
    }
}