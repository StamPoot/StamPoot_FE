package project.android.footstamp.data.login

import android.app.Activity
import android.content.ContentValues.TAG
import android.util.Log
import project.android.footstamp.BuildConfig
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class GoogleLogin(activity: Activity) {
    private val googleSignInOptions =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.GOOGLE_CLIENT_ID)
            .requestServerAuthCode(BuildConfig.GOOGLE_CLIENT_ID)
            .requestEmail()
            .build()

    val signInIntent = GoogleSignIn.getClient(activity, googleSignInOptions).signInIntent

    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>): String? {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            return account?.serverAuthCode
        } catch (e: ApiException) {
            Log.w(TAG, "signInResult:Failed code=${e.statusCode}")
        }
        return null
    }
}