package project.android.footstamp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import project.android.footstamp.ui.theme.MainColor
import project.android.footstamp.ui.theme.SubColor
import project.android.footstamp.ui.theme.TransparentColor
import project.android.footstamp.ui.theme.WhiteColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    text: String,
    backgroundColor: Color = TransparentColor,
    icon: ImageVector? = null,
    iconDrawable: Painter? = null,
    iconColor: Color = MainColor,
    onClickPressed: () -> Unit = {}
) {
    TopAppBar(
        colors = TopAppBarColors(
            backgroundColor,
            WhiteColor,
            WhiteColor,
            WhiteColor,
            WhiteColor
        ),
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TitleLargeText(
                    text = text,
                    MainColor,
                    modifier = Modifier,
                )
                if (icon != null) {
                    Button(
                        onClick = onClickPressed,
                        modifier = Modifier.background(backgroundColor),
                        colors = ButtonColors(
                            containerColor = TransparentColor,
                            contentColor = iconColor,
                            disabledContainerColor = TransparentColor,
                            disabledContentColor = TransparentColor
                        )
                    ) {
                        Icon(icon, null)
                    }
                } else if (iconDrawable != null) {
                    Button(
                        onClick = onClickPressed,
                        modifier = Modifier.background(backgroundColor),
                        colors = ButtonColors(
                            containerColor = TransparentColor,
                            contentColor = iconColor,
                            disabledContainerColor = TransparentColor,
                            disabledContentColor = TransparentColor
                        )
                    ) {
                        Icon(iconDrawable, null)
                    }
                }
            }
        },
    )
    HorizontalDivider(color = SubColor)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBackBar(
    text: String,
    leftIcon: ImageVector,
    rightIcon: ImageVector? = null,
    rightIconDrawable: Painter? = null,
    backgroundColor: Color = SubColor,
    iconColor: Color = MainColor,
    onBackPressed: () -> Unit = {},
    onClickPressed: () -> Unit = {}
) {
    TopAppBar(
        modifier = Modifier,
        colors = TopAppBarColors(
            backgroundColor,
            WhiteColor,
            WhiteColor,
            WhiteColor,
            WhiteColor
        ),
        navigationIcon = {
            Button(
                onClick = onBackPressed,
                modifier = Modifier
                    .background(backgroundColor),
                colors = ButtonColors(
                    containerColor = TransparentColor,
                    contentColor = iconColor,
                    disabledContainerColor = TransparentColor,
                    disabledContentColor = TransparentColor
                )
            ) {
                Icon(leftIcon, null)
            }
        },
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TitleLargeText(text = text, MainColor, modifier = Modifier)
                if (rightIcon != null) {
                    Button(
                        onClick = onClickPressed,
                        modifier = Modifier
                            .background(backgroundColor),
                        colors = ButtonColors(
                            containerColor = TransparentColor,
                            contentColor = iconColor,
                            disabledContainerColor = TransparentColor,
                            disabledContentColor = TransparentColor
                        )
                    ) {
                        Icon(rightIcon, null)
                    }
                } else if (rightIconDrawable != null) {
                    Button(
                        onClick = onClickPressed,
                        modifier = Modifier
                            .background(backgroundColor),
                        colors = ButtonColors(
                            containerColor = TransparentColor,
                            contentColor = iconColor,
                            disabledContainerColor = TransparentColor,
                            disabledContentColor = TransparentColor
                        )
                    ) {
                        Icon(rightIconDrawable, null)
                    }
                }
            }
        },
    )
}