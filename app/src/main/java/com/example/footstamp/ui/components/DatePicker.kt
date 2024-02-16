package com.example.footstamp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.footstamp.data.util.Formatter.convertMillisToDate
import com.example.footstamp.ui.theme.MainColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerView() {
    val datePickerState = rememberDatePickerState(selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            return utcTimeMillis <= System.currentTimeMillis()
        }
    })
    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        DatePicker(
            state = datePickerState
        )
        Spacer(
            modifier = Modifier.height(
                32.dp
            )
        )
        BodyLargeText(
            text = selectedDate.toString(),
            color = MainColor
        )
    }
}