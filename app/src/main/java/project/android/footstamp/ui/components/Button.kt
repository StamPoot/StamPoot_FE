package project.android.footstamp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import project.android.footstamp.ui.theme.MainColor
import project.android.footstamp.ui.theme.SubColor
import project.android.footstamp.ui.theme.TransparentColor
import project.android.footstamp.ui.theme.WhiteColor

@Composable
fun CommonButton(
    text: String = "",
    buttonColor: Color = MainColor,
    textColor: Color = WhiteColor,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .background(TransparentColor)
            .clip(CircleShape),
        colors = CardDefaults.outlinedCardColors(TransparentColor)
    ) {
        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .background(TransparentColor),
            colors = ButtonDefaults.outlinedButtonColors(buttonColor),
            onClick = onClick
        ) {
            BodyLargeText(text, textColor)
        }
    }
}

@Composable
fun ImageButton(
    image: Int,
    buttonWidth: Dp,
    onClick: () -> Unit = {}
) {

    AsyncImage(
        model = image,
        modifier = Modifier
            .size(width = buttonWidth, height = buttonWidth * 8 / 35)
            .clickable { onClick() },
        contentDescription = null,
        contentScale = ContentScale.Crop,
    )
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
        modifier = Modifier.background(TransparentColor),
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
            maxLines = 1,
            textDecoration = if (underLine) TextDecoration.Underline else TextDecoration.None
        )
    }
}

@Composable
fun TransparentButton(
    modifier: Modifier = Modifier.fillMaxSize(),
    onClick: () -> Unit,
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    Box(
        modifier = modifier.clickable(interactionSource, null) { onClick() }
    ) {}
}