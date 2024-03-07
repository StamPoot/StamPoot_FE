package com.example.footstamp.data.util

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
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

    fun localDateTimeToLong(time: LocalDateTime): Long {
        return time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    fun longToLocalDateTime(time: Long): LocalDateTime {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault())
    }

    fun convertBitmapToString(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    fun convertStringToBitmap(encodedString: String): Bitmap {
        val encodeByte = Base64.decode(encodedString, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
    }

}