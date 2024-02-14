package com.example.footstamp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.footstamp.ui.theme.MainColor

@Composable
fun AddButton(text: String = "", color: Color = MainColor, onClick: () -> Unit = {}) {
    Card(
        modifier = Modifier
            .background(Color.Transparent)
            .clip(CircleShape),
        colors = CardDefaults.outlinedCardColors(Color.Transparent)
    ) {
        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .background(Color.Transparent),
            colors = ButtonDefaults.outlinedButtonColors(color),
            onClick = onClick
        ) {
            BodyLargeText(text, Color.White)
        }
    }
}