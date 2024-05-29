package project.android.footstamp.ui.activity

import project.android.footstamp.data.model.LoginToken
import project.android.footstamp.data.model.Provider
import project.android.footstamp.data.util.Formatter
import project.android.footstamp.ui.base.BaseViewModel
import project.android.footstamp.ui.view.gallery.DeleteAllDaoUseCase
import project.android.footstamp.ui.view.login.DeleteTokenDaoUseCase
import project.android.footstamp.ui.view.login.FetchAccessTokenUseCase
import project.android.footstamp.ui.view.login.GetTokenDaoUseCase
import project.android.footstamp.ui.view.login.SetTokenDaoUseCase
import project.android.footstamp.ui.view.profile.DeleteProfileDaoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val fetchAccessTokenUseCase: FetchAccessTokenUseCase,
    private val setTokenDaoUseCase: SetTokenDaoUseCase,
    private val getTokenDaoUseCase: GetTokenDaoUseCase,
    private val deleteTokenDaoUseCase: DeleteTokenDaoUseCase,
    private val deleteAllDaoUseCase: DeleteAllDaoUseCase,
    private val deleteProfileDaoUseCase: DeleteProfileDaoUseCase
) : BaseViewModel() {

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
            getTokenDaoUseCase().let { token ->
                if (token != null) _loginToken.value = token.token
            }
        }
    }

    private fun deleteInformation() {
        coroutineLoading {
            deleteAllDaoUseCase()
            deleteProfileDaoUseCase()
            deleteTokenDaoUseCase()
        }
    }

    fun updateGoogleIdToken(googleToken: String) {
        _googleIdToken.value = googleToken
    }

    fun googleAccessTokenLogin() {
        coroutineLoading {
            fetchAccessTokenUseCase(Provider.GOOGLE, _googleIdToken.value!!)
                .let { authToken ->
                    if (authToken != null) {
                        val loginToken = LoginToken(
                            provider = Provider.GOOGLE,
                            date = Formatter.localTimeToDiaryString(LocalDateTime.now()),
                        ).apply { insertToken(authToken) }

                        setTokenDaoUseCase(loginToken)
                        _loginToken.value = authToken
                    }
                }
        }
    }

    fun kakaoAccessTokenLogin(token: String) {
        coroutineLoading {
            fetchAccessTokenUseCase(Provider.KAKAO, token)
                .let { authToken ->
                    if (authToken != null) {
                        val loginToken = LoginToken(
                            provider = Provider.KAKAO,
                            date = Formatter.localTimeToDiaryString(LocalDateTime.now())
                        ).apply { insertToken(authToken) }

                        setTokenDaoUseCase(loginToken)
                        _loginToken.value = authToken
                    }
                }
        }
    }
}