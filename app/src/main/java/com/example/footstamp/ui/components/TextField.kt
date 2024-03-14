package com.example.footstamp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.footstamp.ui.theme.BackColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInput(
    maxLines: Int = 1,
    minLines: Int = 1,
    modifier: Modifier = Modifier.fillMaxWidth().background(BackColor),
    hint: String = "",
    onValueChange: (text: String) -> Unit
) {
    val textState = remember {
        mutableStateOf("")
    }

    TextField(
        value = textState.value,
        shape = CircleShape,
        modifier = modifier,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
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