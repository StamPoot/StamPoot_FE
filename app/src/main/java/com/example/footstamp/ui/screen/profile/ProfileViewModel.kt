package com.example.footstamp.ui.screen.profile

import androidx.lifecycle.viewModelScope
import com.example.footstamp.data.model.Profile
import com.example.footstamp.data.repository.ProfileRepository
import com.example.footstamp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository
) : BaseViewModel() {

    private val _profileState = MutableStateFlow(Profile())
    val profileState = _profileState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.profile.distinctUntilChanged().collect { profile ->
                _profileState.value = profile
            }
        }
    }
}