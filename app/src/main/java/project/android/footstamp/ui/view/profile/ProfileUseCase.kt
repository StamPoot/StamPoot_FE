package project.android.footstamp.ui.view.profile

import android.content.Context
import project.android.footstamp.data.model.Notification
import project.android.footstamp.data.model.Profile
import project.android.footstamp.data.repository.ProfileRepository
import javax.inject.Inject

class FetchProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(isProfileExist: Boolean): Profile? {
        return profileRepository.fetchProfile(isProfileExist)
    }
}

class UpdateProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(
        profile: Profile,
        context: Context,
        isProfileExist: Boolean
    ): Boolean {
        return profileRepository.updateProfile(profile, context, isProfileExist)
    }
}

class FetchNotificationUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(): List<Notification>? {
        return profileRepository.fetchNotification()
    }
}

class DeleteUserUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(): Boolean {
        return profileRepository.deleteUser()
    }
}

class GetProfileDaoUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(): Profile {
        return profileRepository.getProfileDao()
    }
}

class InsertProfileDaoUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(profile: Profile) {
        return profileRepository.insertProfileDao(profile)
    }
}

class UpdateProfileDaoUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(profile: Profile) {
        return profileRepository.updateProfileDao(profile)
    }
}

class DeleteProfileDaoUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke() {
        return profileRepository.deleteProfileDao()
    }
}

class LogOutProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke() {
        return profileRepository.logOut()
    }
}