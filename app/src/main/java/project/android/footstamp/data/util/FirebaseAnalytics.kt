package project.android.footstamp.data.util

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

class FirebaseAnalytics(context: Context) {

    private val firebaseAnalytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(context)

    fun logCustomEvent(eventName: String, params: Map<String, String>) {
        val bundle = Bundle().apply {
            for ((key, value) in params) {
                putString(key, value)
            }
        }
        firebaseAnalytics.logEvent(eventName, bundle)
    }
}