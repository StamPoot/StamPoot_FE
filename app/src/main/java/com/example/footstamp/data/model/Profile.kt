package com.example.footstamp.data.model

import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.ui.res.painterResource
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.footstamp.R
import com.example.footstamp.data.util.Formatter
import com.example.footstamp.ui.components.uriToBitmap

@Entity(tableName = "profile")
class Profile(
    @ColumnInfo(name = "profile_uid") var uid: String = "uid",
    @ColumnInfo(name = "profile_nickname") var nickname: String = "닉네임을 설정해주세요",
    @ColumnInfo(name = "profile_image") var image: String? = null,
    @ColumnInfo(name = "profile_about_me") var aboutMe: String = "자기소개를 설정해주세요",
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    override fun toString(): String {
        return "uid = $uid nickname = $nickname about me = $aboutMe"
    }

    fun checkProfile(): String? {
        if (nickname.length < 3) return "닉네임이 너무 짧습니다."
        if (aboutMe.length < 5) return "자기소개가 너무 짧습니다."

        return null
    }
}