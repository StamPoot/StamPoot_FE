package com.example.footstamp.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.footstamp.ui.theme.SubColor


@Composable
fun TitleLargeText(
    text: String,
    color: Color = SubColor,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    fontSize: TextUnit = 24.sp
) {
    Text(
        text = text,
        color = color,
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier,
        textAlign = textAlign,
        fontSize = fontSize
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
        textAlign = textAlign,
        fontSize = 20.sp
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
        textAlign = textAlign,
        fontSize = 18.sp
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
        textAlign = textAlign,
        fontSize = 14.sp
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
        textAlign = textAlign,
        fontSize = 10.sp
    )
}