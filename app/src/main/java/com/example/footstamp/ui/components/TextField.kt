package com.example.footstamp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.footstamp.ui.theme.BackColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryInput() {
    val itemHeight = LocalConfiguration.current.screenHeightDp.dp
    val textState = remember {
        mutableStateOf("")
    }

    TextField(
        value = textState.value,
        shape = CircleShape,
        modifier = Modifier
            .fillMaxWidth()
            .height(itemHeight / 3)
            .verticalScroll(rememberScrollState())
            .background(BackColor)
            .padding(5.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.Black,
            focusedBorderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
        ),
        maxLines = 15,
        textStyle = MaterialTheme.typography.bodyLarge,
        onValueChange = { textValue -> textState.value = textValue })
}