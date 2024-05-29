package project.android.footstamp.data.util

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenManager @Inject constructor(@ApplicationContext context: Context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences("token_prefs", Context.MODE_PRIVATE)

    var accessToken: String?
        get() = preferences.getString("ACCESS_TOKEN", null)
        set(value) = preferences.edit().putString("ACCESS_TOKEN", value).apply()
}