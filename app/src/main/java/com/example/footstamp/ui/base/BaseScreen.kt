package com.example.footstamp.ui.base

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.footstamp.ui.screen.gallery.GalleryFloatingButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScreen(
    modifier: Modifier = Modifier,
    floatingButton: @Composable () -> Unit = {},
    content: @Composable (paddingValue: PaddingValues) -> Unit,
) {
    val isLoading by remember {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = modifier,
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = floatingButton
    ) { paddingValue ->
        content(paddingValue)
    }
}