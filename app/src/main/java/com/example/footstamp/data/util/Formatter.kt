package com.example.footstamp.data.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.footstamp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.net.URL
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

    fun dateStringToString(dateString: String): String {
        val year = dateString.substring(0..3)
        val month = dateString.substring(5..6)
        val day = dateString.substring(8..9)
        val hour = dateString.substring(11..12)
        val minute = dateString.substring(14..15)
        val second = dateString.substring(17..18)
        return "$year-$month-$day $hour:$minute:$second"
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

    fun drawableToBitmap(context: Context, drawable: Int): Bitmap {
        return ContextCompat.getDrawable(context, drawable)?.toBitmap()!!
    }

    suspend fun fetchImageBitmap(imageUrl: String): Bitmap? {
        var bitmap: Bitmap? = null
        withContext(Dispatchers.IO) {
            try {
                val url = URL(imageUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()
                val inputStream = connection.inputStream
                bitmap = BitmapFactory.decodeStream(inputStream)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return bitmap
    }
}