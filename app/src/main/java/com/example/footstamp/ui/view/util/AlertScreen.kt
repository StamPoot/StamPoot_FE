package com.example.footstamp.ui.view.util

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.footstamp.R
import com.example.footstamp.data.model.Alert
import com.example.footstamp.data.model.ButtonCount
import com.example.footstamp.ui.components.CommonButton
import com.example.footstamp.ui.components.HalfDialog
import com.example.footstamp.ui.components.SpaceMaker
import com.example.footstamp.ui.components.TitleLargeText
import com.example.footstamp.ui.components.TitleText
import com.example.footstamp.ui.theme.BlackColor
import com.example.footstamp.ui.theme.MainColor
import com.example.footstamp.ui.theme.SubColor
import com.example.footstamp.ui.theme.TransparentColor
import com.example.footstamp.ui.theme.WarnColor
import com.example.footstamp.ui.theme.WhiteColor

@Composable
fun AlertScreen(alert: Alert) {
    HalfDialog(onChangeState = {}) {
        Card(
            modifier = Modifier.fillMaxWidth(0.9f),
            colors = CardColors(
                WhiteColor, TransparentColor, TransparentColor, TransparentColor
            ),
            border = BorderStroke(0.1.dp, SubColor),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                SpaceMaker(height = 10.dp)
                TitleLargeText(
                    text = stringResource(alert.title),
                    color = MainColor,
                    textAlign = TextAlign.Center
                )
                SpaceMaker(height = 10.dp)
                TitleText(
                    text = stringResource(alert.message),
                    color = BlackColor,
                    textAlign = TextAlign.Center
                )
                SpaceMaker(height = 5.dp)
                if (alert.errorMessage != null)
                    TitleText(
                        text = alert.errorMessage,
                        color = BlackColor,
                        textAlign = TextAlign.Center
                    )
                SpaceMaker(height = 5.dp)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    CommonButton(stringResource(R.string.confirm)) { alert.onPressYes() }
                    if (alert.buttonCount == ButtonCount.TWO) {
                        CommonButton(
                            stringResource(R.string.cancel), buttonColor = WarnColor
                        ) { alert.onPressNo() }
                    }
                }
                SpaceMaker(height = 10.dp)
            }
        }
    }
}