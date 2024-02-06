package com.example.footstamp.data.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import java.time.LocalDateTime

class ListConverters {

    @TypeConverter
    fun listToJson(value: List<String?>): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<String>? {
        return Gson().fromJson(value, Array<String>::class.java)?.toList()
    }

    @TypeConverter
    fun longToDate(value: String?): LocalDateTime? {
        return value?.let { LocalDateTime.parse(it) }
    }

    @TypeConverter
    fun dateToLong(date: LocalDateTime?): String? {
        return date?.toString()
    }
}