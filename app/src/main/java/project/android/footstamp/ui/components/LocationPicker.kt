package project.android.footstamp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import project.android.footstamp.data.util.SeoulLocation
import project.android.footstamp.ui.theme.WhiteColor

@Composable
fun LocationPickerView(onClick: (seoulLocation: SeoulLocation) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth(0.9f)) {
        SeoulLocation.entries.forEach { seoulLocation ->
            SpaceMaker(height = 10.dp)
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = { onClick(seoulLocation) }) {
                TitleLargeText(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    text = seoulLocation.location,
                    color = WhiteColor
                )
            }
            SpaceMaker(height = 10.dp)
        }
    }
}