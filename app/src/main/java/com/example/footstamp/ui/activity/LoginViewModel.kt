package com.example.footstamp.ui.activity

import com.example.footstamp.data.model.LoginToken
import com.example.footstamp.data.model.Provider
import com.example.footstamp.data.repository.DiaryRepository
import com.example.footstamp.data.repository.LoginRepository
import com.example.footstamp.data.repository.ProfileRepository
import com.example.footstamp.data.util.Formatter
import com.example.footstamp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val diaryRepository: DiaryRepository,
    private val profileRepository: ProfileRepository
) :
    BaseViewModel() {
    private val _googleIdToken = MutableStateFlow<String?>(null)

    private val _kakaoIdToken = MutableStateFlow<String?>(null)

    private val _loginToken = MutableStateFlow<String?>(null)
    val loginToken = _loginToken.asStateFlow()

    init {
        getTokenFromDB().let {
            if (_loginToken.value == null) deleteInformation()
        }
    }

    private fun getTokenFromDB() {
        coroutineLoading {
            loginRepository.getTokenDao().let { token ->
                if (token != null) _loginToken.value = token.token
            }
        }
    }

    private fun deleteInformation() {
        coroutineLoading {
            diaryRepository.deleteAllDao()
            profileRepository.deleteProfileDao()
        }
    }

    fun updateGoogleIdToken(googleToken: String) {
        _googleIdToken.value = googleToken
    }

    fun googleAccessTokenLogin() {
        coroutineLoading {
            loginRepository.accessTokenLogin(Provider.GOOGLE, _googleIdToken.value!!)
                .also {
                    val loginToken = LoginToken(
                        provider = Provider.GOOGLE,
                        date = Formatter.localTimeToDiaryString(LocalDateTime.now()),
                    ).apply { it.body()?.auth?.let { token -> insertToken(token) } }
                    loginRepository.setTokenDao(loginToken)
                    _loginToken.value = it.body()?.auth
                }
        }
    }

    fun kakaoAccessTokenLogin(token: String) {
        coroutineLoading {
            loginRepository.accessTokenLogin(Provider.KAKAO, token)
                .also { _loginToken.value = token }
        }
    }

    fun kakaoLogin() {
        coroutineLoading {
            loginRepository.kakaoLogin()
        }
    }
}