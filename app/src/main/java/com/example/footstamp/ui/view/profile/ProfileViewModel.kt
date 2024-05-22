package com.example.footstamp.ui.view.profile

import android.content.Context
import android.content.Intent
import com.example.footstamp.data.model.Alert
import com.example.footstamp.data.model.ButtonCount
import com.example.footstamp.data.model.Notification
import com.example.footstamp.data.model.Profile
import com.example.footstamp.data.repository.LoginRepository
import com.example.footstamp.data.repository.ProfileRepository
import com.example.footstamp.ui.activity.LoginActivity
import com.example.footstamp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository, private val loginRepository: LoginRepository
) : BaseViewModel() {

    private val _profileState = MutableStateFlow(Profile())
    val profileState = _profileState.asStateFlow()

    private val _editProfile = MutableStateFlow<Profile?>(null)
    val editProfile = _editProfile.asStateFlow()

    private val _isProfileExist = MutableStateFlow(false)
    val isProfileExist = _isProfileExist.asStateFlow()

    private val _notificationList = MutableStateFlow<List<Notification>>(emptyList())
    val notificationList = _notificationList.asStateFlow()

    private val _profileDeleteText = MutableStateFlow<String?>(null)
    val profileDeleteText = _profileDeleteText.asStateFlow()

    init {
        getProfileFromDB()
        getProfile()
        getNotificationList()
    }

    private fun getProfile() {
        coroutineLoading {
            profileRepository.getProfile(_isProfileExist.value).let { profile ->
                if (profile != null) {
                    _profileState.value = profile
                    _isProfileExist.value = true
                } else {
                    _profileState.value =
                        Profile(uid = "", nickname = SET_NICKNAME, aboutMe = SET_MESSAGE)
                }
            }
        }
    }

    private fun getProfileFromDB() {
        coroutineLoading {
            profileRepository.getProfileDao().let { profile ->
                if (profile != null) {
                    _profileState.value = profile
                    _isProfileExist.value = true
                }
            }
        }
    }

    fun updateEditProfile(
        uid: String = _editProfile.value!!.uid,
        nickname: String = _editProfile.value!!.nickname,
        image: String? = _editProfile.value!!.image,
        aboutMe: String = _editProfile.value!!.aboutMe,
    ) {
        _editProfile.value = Profile(uid, nickname, image, aboutMe)
    }

    fun updateProfile(context: Context): Boolean {
        if (_editProfile.value == null) return false
        if (_editProfile.value!!.checkProfile() != null) return false

        coroutineLoading {
            profileRepository.updateProfile(_editProfile.value!!, context, _isProfileExist.value)
                .let { isSuccessful ->
                    if (isSuccessful) {
                        getProfile()
                        _isProfileExist.value = true
                    } else showError()
                }
            _editProfile.value = null
        }
        return true
    }

    private fun getNotificationList() {
        coroutineLoading {
            profileRepository.getNotification().let {
                if (it != null) {
                    _notificationList.value = it
                }
            }
        }
    }

    fun checkProfileDelete() {
        _profileDeleteText.value = INITIALIZE_CODE
    }

    fun logOutAlert(context: Context) {
        val alert = Alert(title = "로그 아웃 하시겠습니까?",
            message = "",
            buttonCount = ButtonCount.TWO,
            onPressYes = { logOut(context) },
            onPressNo = { hideAlert() })

        showAlert(alert)
    }

    private fun logOut(context: Context) {
        coroutineLoading {
            profileRepository.logOut()
            loginRepository.deleteTokenDao()
            val goHomeActivity = Intent(context, LoginActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            val alert = Alert(title = "로그 아웃 되었습니다",
                message = "",
                buttonCount = ButtonCount.ONE,
                onPressYes = {
                    context.startActivity(goHomeActivity)
                })

            showAlert(alert)
        }
    }

    fun deleteProfileAlert(deleteText: String, context: Context) {
        if (deleteText == DELETE_CODE) {
            val alert = Alert(title = "정말 회원 탈퇴하시겠습니까?",
                message = "회원 정보는 모두 삭제됩니다",
                buttonCount = ButtonCount.TWO,
                onPressYes = { deleteProfile(context) },
                onPressNo = { hideAlert() })

            showAlert(alert)
        }
    }

    private fun deleteProfile(context: Context) {
        coroutineLoading {
            loginRepository.deleteTokenDao()
            profileRepository.deleteUser().let { isSuccessful ->
                if (isSuccessful) {
                    val goHomeActivity = Intent(context, LoginActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    }
                    val alert = Alert(title = "회원 탈퇴되었습니다",
                        message = "",
                        buttonCount = ButtonCount.ONE,
                        onPressYes = { context.startActivity(goHomeActivity) })

                    showAlert(alert)
                } else {
                    showError()
                }
            }
        }
    }

    fun showEditProfileDialog() {
        _editProfile.value = _profileState.value
    }

    fun hideEditProfileDialog() {
        _editProfile.value = null
    }

    companion object {
        const val SET_NICKNAME = "닉네임을 설정해주세요"
        const val SET_MESSAGE = "자기소개를 설정해주세요"
        const val INITIALIZE_CODE = ""
        const val DELETE_CODE = "회원 탈퇴"
    }
}