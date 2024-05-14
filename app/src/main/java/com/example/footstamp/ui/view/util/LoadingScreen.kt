package com.example.footstamp.ui.view.util

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.footstamp.R
import com.example.footstamp.ui.components.SpaceMaker
import com.example.footstamp.ui.components.TitleLargeText
import com.example.footstamp.ui.theme.HalfTransparentColor
import com.example.footstamp.ui.theme.WhiteColor

@Composable
fun LoadingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HalfTransparentColor)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {},
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painterResource(id = R.drawable.icon_transparent),
            contentDescription = null,
            tint = WhiteColor
        )
        SpaceMaker(height = 10.dp)
        TitleLargeText(text = "로딩 중", color = WhiteColor)
    }
}