package com.example.footstamp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile")
class Profile(
    @ColumnInfo(name = "profile_uid") var uid: String = "",
    @ColumnInfo(name = "profile_nickname") var nickname: String = "",
    @ColumnInfo(name = "profile_image") var image: String = "",
    @ColumnInfo(name = "profile_about_me") var aboutMe: String = "",
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    override fun toString(): String {
        return "uid = $uid nickname = $nickname about me = $aboutMe"
    }

    fun checkProfile(): String? {

        return null
    }
}