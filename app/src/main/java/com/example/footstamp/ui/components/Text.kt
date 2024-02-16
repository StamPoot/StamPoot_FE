package com.example.footstamp.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.footstamp.ui.theme.SubColor


@Composable
fun TitleLargeText(text: String, color: Color = SubColor, modifier: Modifier = Modifier) {
    Text(
        text = text,
        color = color,
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier
    )
}

@Composable
fun TitleText(text: String, color: Color = SubColor, modifier: Modifier = Modifier) {
    Text(
        text = text,
        color = color,
        style = MaterialTheme.typography.titleMedium,
        modifier = modifier
    )
}

@Composable
fun BodyLargeText(text: String, color: Color = SubColor, modifier: Modifier = Modifier) {
    Text(
        text = text, color = color,
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier
    )
}

@Composable
fun BodyText(text: String, color: Color = SubColor, modifier: Modifier = Modifier) {
    Text(
        text = text, color = color,
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier
    )
}

@Composable
fun LabelText(text: String, color: Color = SubColor, modifier: Modifier = Modifier) {
    Text(
        text = text,
        color = color,
        style = MaterialTheme.typography.labelMedium,
        modifier = modifier
    )
}