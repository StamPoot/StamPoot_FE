package com.example.footstamp.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.footstamp.ui.theme.SubColor


@Composable
fun TitleLargeText(string: String, color: Color = SubColor) {
    Text(text = string, color = color, style = MaterialTheme.typography.titleLarge)
}

@Composable
fun TitleText(string: String, color: Color = SubColor) {
    Text(text = string, color = color, style = MaterialTheme.typography.titleMedium)
}

@Composable
fun BodyLargeText(string: String, color: Color = SubColor) {
    Text(text = string, color = color, style = MaterialTheme.typography.bodyLarge)
}

@Composable
fun BodyText(string: String, color: Color = SubColor) {
    Text(text = string, color = color, style = MaterialTheme.typography.bodyMedium)
}

@Composable
fun LabelText(string: String, color: Color = SubColor) {
    Text(text = string, color = color, style = MaterialTheme.typography.labelMedium)
}