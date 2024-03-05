package com.example.footstamp.data.model

import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.footstamp.data.util.SeoulLocation
import java.time.LocalDateTime

@Entity(tableName = "diaries")
data class Diary(
    @ColumnInfo(name = "diary_title") var title: String = "",
    @ColumnInfo(name = "diary_date") var date: LocalDateTime = LocalDateTime.now(),
    @ColumnInfo(name = "diary_message") var message: String = "",
    @ColumnInfo(name = "diary_shared") var isShared: Boolean = false,
    @ColumnInfo(name = "diary_location") var location: SeoulLocation = SeoulLocation.CENTRAL,
    @ColumnInfo(name = "diary_photo_urls") val photoURIs: List<String> = emptyList(),
    @ColumnInfo(name = "diary_thumbnail") var thumbnail: Int = 0,
    @ColumnInfo(name = "diary_uid") val uid: String = ""
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    override fun toString(): String {
        return "id = $id, name = $title, message = $message, location = $location"
    }

    fun checkDiary(): Boolean {
        Log.d("TAG",title.length.toString())
        if (title.length < 3 || title.length > 10) return false
        Log.d("TAG",date.toString())
        if (date > LocalDateTime.now()) return false
        Log.d("TAG",message.length.toString())
        if (message.length < 5 || message.length > 100) return false
        Log.d("TAG","3")
        if (photoURIs.isEmpty()) return false
        Log.d("TAG","4")
        if (thumbnail > photoURIs.size - 1) return false
        Log.d("TAG","5")
        return true
    }
}