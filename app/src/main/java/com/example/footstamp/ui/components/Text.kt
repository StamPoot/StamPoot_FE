package com.example.footstamp.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.footstamp.ui.theme.SubColor


@Composable
fun TitleLargeText(
    text: String,
    color: Color = SubColor,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    fontSize: TextUnit = 24.sp,
    minLines: Int = 1
) {
    Text(
        text = text,
        color = color,
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier,
        textAlign = textAlign,
        fontSize = fontSize,
        minLines = minLines
    )
}

@Composable
fun TitleText(
    text: String,
    color: Color = SubColor,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    minLines: Int = 1
) {
    Text(
        text = text,
        color = color,
        style = MaterialTheme.typography.titleMedium,
        modifier = modifier,
        textAlign = textAlign,
        fontSize = 20.sp,
        minLines = minLines
    )
}

@Composable
fun BodyLargeText(
    text: String,
    color: Color = SubColor,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    minLines: Int = 1,
    textDecoration: TextDecoration = TextDecoration.None
) {
    Text(
        text = text, color = color,
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier,
        textAlign = textAlign,
        fontSize = 18.sp,
        minLines = minLines,
        textDecoration = textDecoration
    )
}

@Composable
fun BodyText(
    text: String,
    color: Color = SubColor,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    minLines: Int = 1,
    maxLines: Int = 10
) {
    Text(
        text = text, color = color,
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier,
        textAlign = textAlign,
        fontSize = 16.sp,
        minLines = minLines,
        maxLines = maxLines
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