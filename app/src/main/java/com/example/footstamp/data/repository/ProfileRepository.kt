package com.example.footstamp.data.repository

import android.content.ContentValues.TAG
import android.graphics.BitmapFactory
import android.util.Log
import com.example.footstamp.data.dao.ProfileDao
import com.example.footstamp.data.data_source.UserService
import com.example.footstamp.data.dto.request.user.ProfileUpdateRequestDTO
import com.example.footstamp.data.model.Profile
import com.example.footstamp.data.util.Formatter
import com.example.footstamp.data.util.TokenManager
import com.example.footstamp.ui.base.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val tokenManager: TokenManager,
    private val profileDao: ProfileDao,
    private val userService: UserService
) : BaseRepository() {

    val profile: Flow<Profile> = profileDao.getProfile().flowOn(Dispatchers.IO).conflate()

    suspend fun getProfile(isProfileExist: Boolean) {

        userService.profileGet(tokenManager.accessToken!!).let { response ->
            if (response.isSuccessful) {
                val responseBody = response.body()!!
                val responseImage = Formatter.fetchImageBitmap(responseBody.profileImg)
                val responseProfile = Profile(
                    uid = responseBody.id.toString(),
                    nickname = responseBody.nickname,
                    image = responseImage?.let { Formatter.convertBitmapToString(it) },
                    aboutMe = responseBody.sentence.let { it ?: "" }
                )
                if (isProfileExist) updateProfileDao(responseProfile)
                else insertProfileDao(responseProfile)
            }
        }
    }

    suspend fun updateProfile(profile: Profile) {
        userService.profileEdit(
            tokenManager.accessToken!!,
            ProfileUpdateRequestDTO(
                profile.nickname,
                profile.image,
                profile.aboutMe
            )
        ).let {
            if (it.isSuccessful) {
                updateProfileDao(profile)
            }
        }
    }


    // dao Database

    suspend fun insertProfileDao(profile: Profile) {
        profileDao.setProfile(profile)
    }

    suspend fun updateProfileDao(profile: Profile) {
        profileDao.updateUID(profile.uid)
        profileDao.updateNickname(profile.nickname)
        profile.image?.let { profileDao.updateImage(it) }
        profileDao.updateAboutMe(profile.aboutMe)
    }

    suspend fun deleteProfileDao() {
        profileDao.deleteProfile()
    }
}