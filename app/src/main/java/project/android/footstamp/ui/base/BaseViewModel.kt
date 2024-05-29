package project.android.footstamp.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import project.android.footstamp.R
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import project.android.footstamp.data.model.Alert
import project.android.footstamp.data.model.ButtonCount

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
            } catch (e: Exception) {
                e.message?.let { showError(it) }
            } finally {
                finishLoading()
            }
        }
    }

    fun showError(errorMessage: String? = null) {
        val alert = Alert(
            title = R.string.error_occur,
            message = R.string.error_occur_content,
            errorMessage = errorMessage,
            buttonCount = ButtonCount.ONE,
            onPressYes = { hideAlert() }
        )

        showAlert(alert)
    }
}