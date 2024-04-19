package com.example.footstamp.ui.screen.profile

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.footstamp.data.model.Profile
import com.example.footstamp.data.repository.ProfileRepository
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
    }

    fun showEditProfileDialog() {
        _editProfile.value = _profileState.value
    }

    fun hideEditProfileDialog() {
        _editProfile.value = null
    }

    fun updateEditProfile(
        uid: String = _editProfile.value!!.uid,
        nickname: String = _editProfile.value!!.nickname,
        image: String? = _editProfile.value!!.image,
        aboutMe: String = _editProfile.value!!.aboutMe,
    ) {
        _editProfile.value = Profile(uid, nickname, image, aboutMe)
    }

    fun updateProfile(): Boolean {
        if (_editProfile.value == null) return false
        if (_editProfile.value!!.checkProfile() != null) return false

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if (_isProfileExist.value) repository.updateProfile(_editProfile.value!!)
                else {
                    _isProfileExist.value = true
                    repository.insertProfileDao(_editProfile.value!!)
                }
                _editProfile.value = null
            }
        }
        return true
    }
}