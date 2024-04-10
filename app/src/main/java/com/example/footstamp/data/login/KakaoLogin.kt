package com.example.footstamp.data.login

import android.app.Activity
import android.util.Log
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.user.UserApiClient

class KakaoLogin(val activity: Activity, val getToken: (token: String) -> Unit) {

    private lateinit var kakaoCallback: (OAuthToken?, Throwable?) -> Unit

    init {
        setKakaoCallback()
        kakaoDeleteInfo()
    }

    fun checkKakaoLogin(): Boolean {
        return UserApiClient.instance.isKakaoTalkLoginAvailable(activity)
    }

    fun kakaoAppLogin() {
        UserApiClient.instance.loginWithKakaoTalk(activity, callback = kakaoCallback)
    }

    fun kakaoAccountLogin() {
        UserApiClient.instance.loginWithKakaoAccount(activity, callback = kakaoCallback)
    }

    private fun setKakaoCallback() {
        kakaoCallback = { token, error ->
            if (error != null) {
                when {
                    error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                        Log.d("[카카오로그인]", "접근이 거부 됨(동의 취소)")
                    }

                    error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                        Log.d("[카카오로그인]", "유효하지 않은 앱")
                    }

                    error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                        Log.d("[카카오로그인]", "인증 수단이 유효하지 않아 인증할 수 없는 상태")
                    }

                    error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                        Log.d("[카카오로그인]", "요청 파라미터 오류")
                    }

                    error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                        Log.d("[카카오로그인]", "유효하지 않은 scope ID")
                    }

                    error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                        Log.d("[카카오로그인]", "설정이 올바르지 않음(android key hash)")
                    }

                    error.toString() == AuthErrorCause.ServerError.toString() -> {
                        Log.d("[카카오로그인]", "서버 내부 에러")
                    }

                    error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                        Log.d("[카카오로그인]", "앱이 요청 권한이 없음")
                    }

                    else -> { // Unknown
                        Log.d("[카카오로그인]", "기타 에러")
                    }
                }
            } else if (token != null) {
                Log.d("[카카오로그인]", "로그인에 성공하였습니다.\n${token.accessToken}")
                UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                    getToken(token.accessToken)
                    UserApiClient.instance.me { user, error ->
                        user?.kakaoAccount?.profile?.nickname
                    }
                }
            } else {
                Log.d("카카오로그인", "토큰==null error==null")
            }
        }
    }

    fun kakaoLogOut() {
        UserApiClient.instance.logout { error ->
            if (error != null) {
                Log.d("카카오", "카카오 로그아웃 실패")
                Log.e("카카오 에러", error.message.toString())
            } else {
                Log.d("카카오", "카카오 로그아웃 성공!")
            }
        }
    }

    fun kakaoDeleteInfo() {
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                Log.d("카카오로그인", "회원 탈퇴 실패")
                Log.e("카카오 에러", error.message.toString())
            } else {
                Log.d("카카오로그인", "회원 탈퇴 성공")
            }
        }
    }
}