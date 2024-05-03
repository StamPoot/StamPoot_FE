package com.example.footstamp.ui.screen.profile

import android.content.Context
import android.content.Intent
import androidx.lifecycle.viewModelScope
import com.example.footstamp.data.model.Notification
import com.example.footstamp.data.model.Profile
import com.example.footstamp.data.repository.ProfileRepository
import com.example.footstamp.ui.activity.LoginActivity
import com.example.footstamp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository
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
        viewModelScope.launch(Dispatchers.IO) {
            repository.profile.distinctUntilChanged().collect { profile ->
                if (profile != null) {
                    _profileState.value = profile
                    _isProfileExist.value = true
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            repository.getProfile(_isProfileExist.value)
        }
        viewModelScope.launch(Dispatchers.IO) {
            getNotificationList()
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

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if (_isProfileExist.value) repository.updateProfile(_editProfile.value!!, context)
                else {
                    _isProfileExist.value = true
                    repository.insertProfileDao(_editProfile.value!!)
                }
                _editProfile.value = null
            }
        }
        return true
    }

    fun getNotificationList() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getNotification().let {
                if (it != null) {
                    _notificationList.value = it
                }
            }
        }
    }

    fun checkProfileDelete() {
        _profileDeleteText.value = ""
    }

    fun deleteProfile(deleteText: String, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            if (deleteText == "회원 탈퇴") repository.deleteUser().let { isSuccessful ->
                if (isSuccessful) {
                    context.startActivity(Intent(context, LoginActivity::class.java))
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
}