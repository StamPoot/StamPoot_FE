package com.example.footstamp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.footstamp.ui.theme.BackColor
import com.example.footstamp.ui.theme.MainColor
import com.example.footstamp.ui.theme.SubColor

@Composable
fun AddButton(
    text: String = "",
    color: Color = MainColor,
    onClick: () -> Unit = {}
) {
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

@Composable
fun ChangeButton(
    text: String = "",
    color: Color = SubColor,
    icon: ImageVector? = null,
    underLine: Boolean = false,
    tint: Color,
    onClick: () -> Unit = {}
) {
    Button(
        modifier = Modifier
            .background(Color.Transparent),
        colors = ButtonDefaults.outlinedButtonColors(color),
        shape = RectangleShape,
        onClick = onClick,
    ) {
        if (icon != null) {
            Icon(icon, contentDescription = null, tint = tint)
        }
        SpaceMaker(height = 2.dp)
        BodyLargeText(
            text = text,
            color = tint,
            textDecoration = if (underLine) TextDecoration.Underline else TextDecoration.None
        )
    }
}

@Composable
fun TransparentButton(
    onClick: () -> Unit,
    content: @Composable () -> Unit = {},
) {
    Button(
        modifier = Modifier.fillMaxSize(),
        colors = ButtonColors(
            Color.Transparent,
            Color.Transparent,
            Color.Transparent,
            Color.Transparent
        ),
        shape = RectangleShape,
        onClick = onClick
    ) {}
}