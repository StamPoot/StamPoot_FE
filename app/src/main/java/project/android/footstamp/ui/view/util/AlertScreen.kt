package project.android.footstamp.ui.view.util

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
import project.android.footstamp.R
import project.android.footstamp.data.model.Alert
import project.android.footstamp.data.model.ButtonCount
import project.android.footstamp.ui.components.CommonButton
import project.android.footstamp.ui.components.HalfDialog
import project.android.footstamp.ui.components.SpaceMaker
import project.android.footstamp.ui.components.TitleLargeText
import project.android.footstamp.ui.components.TitleText
import project.android.footstamp.ui.theme.BlackColor
import project.android.footstamp.ui.theme.MainColor
import project.android.footstamp.ui.theme.SubColor
import project.android.footstamp.ui.theme.TransparentColor
import project.android.footstamp.ui.theme.WarnColor
import project.android.footstamp.ui.theme.WhiteColor

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