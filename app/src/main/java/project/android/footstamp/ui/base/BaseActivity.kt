package project.android.footstamp.ui.base

import androidx.activity.ComponentActivity
import project.android.footstamp.data.util.FirebaseAnalytics

open class BaseActivity : ComponentActivity() {

    private lateinit var analytics: FirebaseAnalytics

    fun sendAnalyticsEvent(state: LogState, message: String) {

        val params = mapOf(
            "state" to state.name,
            "message" to message,
        )
        analytics.logCustomEvent(state.name, params)
    }
}

enum class LogState {
    SUCCESS,
    LOGIN_ERROR
}