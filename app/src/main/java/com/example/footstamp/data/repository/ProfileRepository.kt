package com.example.footstamp.data.repository

import android.content.Context
import com.example.footstamp.data.dao.ProfileDao
import com.example.footstamp.data.data_source.UserService
import com.example.footstamp.data.model.Notification
import com.example.footstamp.data.model.Profile
import com.example.footstamp.data.util.Formatter
import com.example.footstamp.data.util.TokenManager
import com.example.footstamp.ui.base.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val tokenManager: TokenManager,
    private val profileDao: ProfileDao,
    private val userService: UserService
) : BaseRepository() {

    suspend fun getProfile(isProfileExist: Boolean): Profile? {
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
                if (isProfileExist) {
                    updateProfileDao(responseProfile)
                } else {
                    insertProfileDao(responseProfile)
                }
                return responseProfile
            }
            return null
        }
    }

    suspend fun updateProfile(
        profile: Profile,
        context: Context,
        isProfileExist: Boolean
    ): Boolean {
        val imageBitmap = profile.image?.let { Formatter.convertStringToBitmap(it) }

        userService.profileEdit(
            tokenManager.accessToken!!,
            Formatter.createPartFromString(profile.nickname),
            imageBitmap?.let { Formatter.convertBitmapToFile("picture", it, context) },
            Formatter.createPartFromString(profile.aboutMe),
        ).let {
            if (it.isSuccessful) {
                if (isProfileExist) updateProfileDao(profile)
                else insertProfileDao(profile)
                return true
            }
        }
        return false
    }

    suspend fun getNotification(): List<Notification>? {
        userService.profileNotification(tokenManager.accessToken!!).let { listResponse ->
            if (listResponse.isSuccessful) {
                val response = listResponse.body()!!
                val notificationList = response.map { notificationDto ->

                    // Profile DTO -> Profile Model
                    val profile = Profile(
                        uid = notificationDto.profileDto.id.toString(),
                        nickname = notificationDto.profileDto.nickname,
                        image = notificationDto.profileDto.profileImg,
                        aboutMe = notificationDto.profileDto.sentence.let { it ?: "" }
                    )

                    // Notification DTO -> Notification Model
                    Notification(
                        profile = profile,
                        content = notificationDto.content,
                        dateTime = notificationDto.dateTime
                    )
                }
                return notificationList
            }
        }
        return null
    }

    fun logOut() {
        tokenManager.accessToken = null
    }

    suspend fun deleteUser(): Boolean {
        userService.profileDelete(tokenManager.accessToken!!).let {
            if (it.isSuccessful) return true
        }
        return false
    }


// dao Database

    suspend fun getProfileDao(): Profile {
        return profileDao.getProfile()
    }

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