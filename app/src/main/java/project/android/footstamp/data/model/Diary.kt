package project.android.footstamp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import project.android.footstamp.data.util.SeoulLocation
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
    @ColumnInfo(name = "diary_uid") val uid: String = "",
    @ColumnInfo(name = "diary_likes") var likes: Int = 0
) {
    @PrimaryKey(autoGenerate = false)
    var id: Long = 0

    override fun toString(): String {
        return "id = $id, name = $title, message = $message, location = $location"
    }

    fun insertId(id: Long) {
        this@Diary.id = id
    }

    fun checkDiary(): String? {
        if (title.length <= 3 || title.length > 10) return DIARY_ERROR_TITLE
        if (date > LocalDateTime.now()) return DIARY_ERROR_DATE
        if (message.length <= 5 || message.length > 100) return DIARY_ERROR_MESSAGE
        if (photoBitmapStrings.isEmpty()) return DIARY_ERROR_PHOTO
        if (thumbnail > photoBitmapStrings.size - 1) return DIARY_ERROR_THUMBNAIL
        return null
    }

    companion object {
        const val DIARY_ERROR_TITLE = "제목은 3자에서 10자로 지어주세요"
        const val DIARY_ERROR_DATE = "미래의 일기는 작성할 수 없어요"
        const val DIARY_ERROR_MESSAGE = "내용은 5~100자로 작성해주세요"
        const val DIARY_ERROR_PHOTO = "사진을 넣어주세요"
        const val DIARY_ERROR_THUMBNAIL = "썸네일을 지정해주세요"
    }
}