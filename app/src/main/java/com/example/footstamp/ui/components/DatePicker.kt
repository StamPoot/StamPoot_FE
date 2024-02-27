package com.example.footstamp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.footstamp.data.util.Formatter.convertMillisToDate
import com.example.footstamp.data.util.Formatter.localDateTimeToLong
import com.example.footstamp.data.util.Formatter.longToLocalDateTime
import com.example.footstamp.ui.theme.MainColor
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Formatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerView(
    time: LocalDateTime,
    onChangeState: (LocalDateTime) -> Unit,
    onDismiss: () -> Unit = {}
) {
    val datePickerState = rememberDatePickerState(selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            return utcTimeMillis <= System.currentTimeMillis()
        }
    })
    // 현재 시간을 가져오기
    val selectedDate = convertMillisToDate(localDateTimeToLong(time))

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        DatePicker(state = datePickerState,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            headline = {
                BodyLargeText(
                    text = selectedDate,
                    color = MainColor,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            })
        SpaceMaker(32.dp)
        Button(onClick = {
            datePickerState.selectedDateMillis?.let {
                onChangeState(longToLocalDateTime(it))
            }
            onDismiss()
        }) {
            BodyLargeText(text = "확인", color = Color.White)
        }
        SpaceMaker(height = 32.dp)
    }
}