package com.example.footstamp.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun startLoading() {
        _isLoading.value = true
    }

    fun finishLoading() {
        _isLoading.value = false
    }

    fun coroutineLoading(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        operation: suspend () -> Unit
    ) {
        viewModelScope.launch(dispatcher) {
            try {
                startLoading()
                operation()
            } finally {
                finishLoading()
            }
        }
    }
}