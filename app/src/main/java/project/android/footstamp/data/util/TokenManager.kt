package project.android.footstamp.data.util

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "token_prefs")

class TokenManager @Inject constructor(@ApplicationContext private val context: Context) {
    companion object {
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("ACCESS_TOKEN")
    }

    val accessToken: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[ACCESS_TOKEN_KEY]
        }

    suspend fun updateAccessToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = token
        }
    }

    suspend fun clearAccessToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(ACCESS_TOKEN_KEY)
        }
    }
}
