package com.example.footstamp.data.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import java.util.Date

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
    fun longToDate(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToLong(date: Date?): Long? {
        return date?.time?.toLong()
    }
}