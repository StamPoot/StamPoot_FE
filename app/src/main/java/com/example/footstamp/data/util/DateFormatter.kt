package com.example.footstamp.data.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateFormatter {

    fun dateToString(date: LocalDateTime, dateFormat: String = "yyyy년 MM월 dd일"): String {

        return date.format(DateTimeFormatter.ofPattern(dateFormat))
    }

}