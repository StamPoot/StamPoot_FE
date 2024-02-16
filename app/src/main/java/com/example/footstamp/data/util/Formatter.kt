package com.example.footstamp.data.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

object Formatter {

    fun dateToString(date: LocalDateTime, dateFormat: String = "yyyy년 MM월 dd일"): String {

        return date.format(DateTimeFormatter.ofPattern(dateFormat))
    }

    @SuppressLint("SimpleDateFormat")
    fun convertMillisToDate(millis: Long): String {
        val formatter = SimpleDateFormat("yyyy년 MM월 dd일")
        return formatter.format(Date(millis))
    }

}