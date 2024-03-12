package com.example.footstamp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.footstamp.data.model.Profile
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setProfile(profile: Profile)

    @Query("UPDATE profile SET profile_uid = :uid")
    fun updateUID(uid: String)

    @Query("UPDATE profile SET profile_nickname = :nickname")
    fun updateNickname(nickname: String)

    @Query("UPDATE profile SET profile_image = :image")
    fun updateImage(image: String)

    @Query("UPDATE profile SET profile_about_me = :aboutMe")
    fun updateAboutMe(aboutMe: String)

    @Query("SELECT * FROM profile")
    fun getProfile(): Flow<Profile>
}