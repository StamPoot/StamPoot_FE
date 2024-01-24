package com.example.footstamp.ui.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun BaseScreen(
    baseViewModel: BaseViewModel,
    content: @Composable () -> Unit
) {
    val viewModel = baseViewModel
    val isLoading by remember {
        mutableStateOf(false)
    }
    content()
}