package com.example.footstamp.data.repository

import com.example.footstamp.data.data_source.LoginService
import com.example.footstamp.data.dto.login.LoginGoogleRequestModel
import com.example.footstamp.data.dto.login.LoginGoogleResponseModel
import com.example.footstamp.data.dto.login.Result
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val service: LoginService
) {

    private val getAccessTokenBaseUrl = "https://impine.shop/login/oauth2/code/google"
    private val sendAccessTokenBaseUrl = "https://impine.shop/"

    private val clientID = "59599924227-masr58k95rljkkfa6oglphbiufnadatc.apps.googleusercontent.com"
    private val clientSecret = "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"

    suspend fun login(provider: LoginService.Provider, token: String) =
        LoginService.loginRetrofit(getAccessTokenBaseUrl).getLoginToken(provider.provider, token)

    suspend fun fetchGoogleAuthInfo(
        authCode: String
    ): Result<LoginGoogleResponseModel> {
        service.fetchGoogleAuthInfo(
            LoginGoogleRequestModel(
                grant_type = "authorization_code",
                client_id = clientID,
                client_secret = clientSecret,
                redirect_uri = getAccessTokenBaseUrl,
                code = authCode
            )
        )?.run {
            return Result.Success(this.body() ?: LoginGoogleResponseModel())
        } ?: return Result.Error(Exception("Retrofit Exception"))
    }
}