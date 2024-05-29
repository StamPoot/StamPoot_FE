package project.android.footstamp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile")
class Profile(
    @ColumnInfo(name = "profile_uid") var uid: String = "",
    @ColumnInfo(name = "profile_nickname") var nickname: String = "",
    @ColumnInfo(name = "profile_image") var image: String? = null,
    @ColumnInfo(name = "profile_about_me") var aboutMe: String = "",
) {
    @PrimaryKey(autoGenerate = false)
    var id: Long = 0

    override fun toString(): String {
        return "id = $id uid = $uid nickname = $nickname about me = $aboutMe"
    }

    fun checkProfile(): String? {
        if (nickname.length < 3) return PROFILE_ERROR_NICKNAME
        if (aboutMe.length < 5) return PROFILE_ERROR_ABOUT_ME

        return null
    }

    companion object {
        const val PROFILE_ERROR_NICKNAME = "닉네임이 너무 짧습니다."
        const val PROFILE_ERROR_ABOUT_ME = "자기소개가 너무 짧습니다."
    }
}