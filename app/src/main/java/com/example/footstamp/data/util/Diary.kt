package com.example.footstamp.data.util

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "diarys")
data class Diary(
    @ColumnInfo(name = "diary_title") var title: String,
    @ColumnInfo(name = "diary_date") var date: Date,
    @ColumnInfo(name = "diary_message") var message: String,
    @ColumnInfo(name = "diary_shared") var isShared: Boolean = false,
    @ColumnInfo(name = "diary_location") var location: SeoulLocation,
    @ColumnInfo(name = "diary_photo_urls") val photoURLs: List<String>,
    @ColumnInfo(name = "diary_thumbnail") var thumbnail: Int,
    @ColumnInfo(name = "diary_uid") val uid: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    override fun toString(): String {
        return "id = $id, name = $title, message = $message, location = $location"
    }
}