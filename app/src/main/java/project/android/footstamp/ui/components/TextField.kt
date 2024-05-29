package project.android.footstamp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import project.android.footstamp.ui.theme.BackColor
import project.android.footstamp.ui.theme.TransparentColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInput(
    maxLines: Int = 1,
    minLines: Int = 1,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .background(BackColor),
    hint: String = "",
    baseText: String = "",
    textState: MutableState<String> = remember { mutableStateOf(baseText) },
    onValueChange: (text: String) -> Unit
) {

    TextField(
        value = textState.value,
        shape = CircleShape,
        modifier = modifier,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = TransparentColor,
            disabledBorderColor = TransparentColor,
            unfocusedBorderColor = TransparentColor,
        ),
        maxLines = maxLines,
        minLines = minLines,
        singleLine = maxLines == 1,
        textStyle = MaterialTheme.typography.bodyLarge,
        onValueChange = { textValue ->
            textState.value = textValue
            onValueChange(textValue)
        },
        placeholder = { BodyLargeText(text = hint) },
    )
}