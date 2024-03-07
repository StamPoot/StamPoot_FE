package com.example.footstamp.data.model

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
    @ColumnInfo(name = "diary_photo_bitmaps") val photoBitmapStrings: List<String> = emptyList(),
    @ColumnInfo(name = "diary_thumbnail") var thumbnail: Int = 0,
    @ColumnInfo(name = "diary_uid") val uid: String = ""
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    override fun toString(): String {
        return "id = $id, name = $title, message = $message, location = $location"
    }

    fun checkDiary(): String? {
        if (title.length <= 3 || title.length > 10) return "제목은 3자에서 10자로 지어주세요"
        if (date > LocalDateTime.now()) return "미래의 일기는 작성할 수 없어요"
        if (message.length <= 5 || message.length > 100) return "내용은 5~100자로 작성해주세요"
        if (photoBitmapStrings.isEmpty()) return "사진을 넣어주세요"
        if (thumbnail > photoBitmapStrings.size - 1) return "썸네일을 지정해주세요"
        return null
    }
}