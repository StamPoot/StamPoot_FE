package com.example.footstamp.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.example.footstamp.ui.theme.SubColor


@Composable
fun TitleLargeText(
    text: String,
    color: Color = SubColor,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = text,
        color = color,
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier,
        textAlign = textAlign
    )
}

@Composable
fun TitleText(
    text: String,
    color: Color = SubColor,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = text,
        color = color,
        style = MaterialTheme.typography.titleMedium,
        modifier = modifier,
        textAlign = textAlign
    )
}

@Composable
fun BodyLargeText(
    text: String,
    color: Color = SubColor,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = text, color = color,
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier,
        textAlign = textAlign
    )
}

@Composable
fun BodyText(
    text: String,
    color: Color = SubColor,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = text, color = color,
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier,
        textAlign = textAlign
    )
}

@Composable
fun LabelText(
    text: String,
    color: Color = SubColor,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = text,
        color = color,
        style = MaterialTheme.typography.labelMedium,
        modifier = modifier,
        textAlign = textAlign
    )
}