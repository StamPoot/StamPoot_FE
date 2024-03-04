package com.example.footstamp.ui.screen.map

import com.example.footstamp.data.util.SeoulLocation
import com.example.footstamp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor() : BaseViewModel() {
    private val _screenMapState = MutableStateFlow<SeoulLocation?>(null)
    val screenMapState = _screenMapState.asStateFlow()

    fun updateMapState(location: SeoulLocation) {
        _screenMapState.value = location
    }

    fun dismissMapState() {
        _screenMapState.value = null
    }
}