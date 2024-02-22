package com.example.footstamp.ui.activity

import android.app.Activity
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.footstamp.ui.screen.login.LoginViewModel
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.BuildConfig
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    private val viewModel by viewModels<LoginViewModel>()

    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest
    private lateinit var idToken: String

    private val oneTapClientResult =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                try {
                    // 인텐트로 부터 로그인 자격 정보를 가져옴
                    val credential = oneTapClient.getSignInCredentialFromIntent(result.data)
                    // 가져온 자격 증명에서 Google ID 토큰을 추출
                    val googleIdToken = credential.googleIdToken
                    if (googleIdToken != null) {
                        // Google ID 토큰을 사용해 Firebase 인증 자격 증명을 생성
                        val firebaseCredential =
                            GoogleAuthProvider.getCredential(googleIdToken, null)
                        // 생성된 Firebase 인증 자격 증명을 사용하여 Firebase 에 로그인을 시도
                        Firebase.auth.signInWithCredential(firebaseCredential)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Firebase.auth.currentUser?.getIdToken(true)
                                        ?.addOnCompleteListener { idTokenTask ->
                                            if (idTokenTask.isSuccessful) {
                                                idTokenTask.result?.token?.let { token ->
                                                    idToken = token
//                                                    viewModel.login(idToken)
                                                } ?: Log.d("FirebaseIdToken is null.", "")
                                            }
                                        }
                                } else {
                                    Log.d("task.exception", "")
                                }
                            }
                    } else {
                        Log.d("", "GoogleIdToken is null.")
                    }
                } catch (exception: ApiException) {
                    Log.d("", exception.localizedMessage)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initGoogleLogin()
//        initListener()
//        initObserver()
    }

    private fun initGoogleLogin() {
        // 현쟈 액티비티(this)에 대한 Google One Tap 로그인 클라이언트를 가져옴
        oneTapClient = Identity.getSignInClient(this)
        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    // Google Id Token 기반 로그인을 지원하도록 설정
                    .setSupported(true)
                    // 서버의 클라이언트 ID 를 설정
//                    .setServerClientId(BuildConfig.GOOGLE_CLIENT_ID)
                    // 기존에 인증된 계정만을 필터링하지 않도록 설정
                    .setFilterByAuthorizedAccounts(false)
                    .build(),
            )
            // 이전에 선택 했던 계정을 기억
            .setAutoSelectEnabled(true)
            .build()
    }

    private fun initListener() {
//        binding.cvGoogleLogin.setOnClickListener {
//            oneTapClient
//                .beginSignIn(signInRequest)
//                .addOnSuccessListener(this) { result ->
//                    try {
//                        oneTapClientResult.launch(
//                            IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
//                        )
//                    } catch (exception: IntentSender.SendIntentException) {
//                        Log.d("","Couldn't start One Tap UI: ${exception.localizedMessage}")
//                    }
//                }
//                .addOnFailureListener(this) { exception ->
//                    Log.d("",exception.localizedMessage)
//                }
//        }
    }
}