package com.example.footstamp.data.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

object Formatter {

    fun dateTimeToString(date: LocalDateTime): String {
        val dateFormat = "yyyy년 MM월 dd일"
        return date.format(DateTimeFormatter.ofPattern(dateFormat))
    }

    fun dateTimeToFormedString(dateString: String): String {
        val year = dateString.substring(0..3)
        val month = dateString.substring(5..6)
        val day = dateString.substring(8..9)
        val hour = dateString.substring(11..12)
        val minute = dateString.substring(14..15)
        val second = dateString.substring(17..18)
        return "$year-$month-$day $hour:$minute:$second"
    }

    fun localTimeToDiaryString(localDateTime: LocalDateTime): String {
        val dateFormat = "yyyy-MM-dd"
        return localDateTime.format(DateTimeFormatter.ofPattern(dateFormat))
    }

    fun stringToDateTime(dateString: String): LocalDateTime {
        val dateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return LocalDate.parse(dateString, dateTime).atStartOfDay()
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
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    fun convertStringToBitmap(encodedString: String): Bitmap {
        val encodeByte = Base64.decode(encodedString, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
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

    fun createPartFromString(value: String): RequestBody {
        return value.toRequestBody(MultipartBody.FORM)
    }

    fun convertBitmapToFile(
        fieldName: String,
        bitmap: Bitmap,
        context: Context
    ): MultipartBody.Part {
        val file = File(context.cacheDir, fieldName)
        file.createNewFile()

        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val bitmapData = byteArrayOutputStream.toByteArray()

        FileOutputStream(file).apply {
            write(bitmapData)
            flush()
            close()
        }

        val requestFile = file.asRequestBody("image/png".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(fieldName, file.name, requestFile)
    }
}