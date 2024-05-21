package com.example.footstamp.ui.base

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.footstamp.data.model.Alert
import com.example.footstamp.data.model.ButtonCount
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _alertState = MutableStateFlow<Alert?>(null)
    val alertState = _alertState.asStateFlow()

    private fun startLoading() {
        _isLoading.value = true
    }

    private fun finishLoading() {
        _isLoading.value = false
    }

    fun showAlert(alert: Alert) {
        _alertState.value = alert
    }

    fun hideAlert() {
        _alertState.value = null
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

    fun showError(errorMessage: String = "") {
        val alert = Alert(
            title = "오류 발생",
            message = errorMessage,
            buttonCount = ButtonCount.ONE,
            onPressYes = { hideAlert() }
        )

        showAlert(alert)
    }
}