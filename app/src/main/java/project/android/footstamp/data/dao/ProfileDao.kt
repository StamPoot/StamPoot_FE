package project.android.footstamp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import project.android.footstamp.data.model.Profile

@Dao
interface ProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setProfile(profile: Profile)

    @Query("UPDATE profile SET profile_uid = :uid")
    fun updateUID(uid: String)

    @Query("UPDATE profile SET profile_nickname = :nickname")
    fun updateNickname(nickname: String)

    @Query("UPDATE profile SET profile_image = :image")
    fun updateImage(image: String?)

    @Query("UPDATE profile SET profile_about_me = :aboutMe")
    fun updateAboutMe(aboutMe: String)

    @Query("SELECT * FROM profile")
    fun getProfile(): Profile

    @Query("DELETE FROM profile")
    fun deleteProfile()
}