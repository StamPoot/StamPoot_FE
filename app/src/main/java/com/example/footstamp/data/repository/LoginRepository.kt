package com.example.footstamp.data.repository

import android.util.Log
import com.example.footstamp.data.data_source.LoginAPI
import com.example.footstamp.data.dto.login.LoginGoogleRequestModel
import com.example.footstamp.data.dto.login.LoginGoogleResponseModel
import com.example.footstamp.data.dto.login.SendAccessTokenModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class LoginRepository @Inject constructor(loginAPI: LoginAPI) {

    private val getAccessTokenBaseUrl = "https://impine.shop/login/oauth2/code/google"
    private val sendAccessTokenBaseUrl = "https://impine.shop/"

    private val clientID = "59599924227-masr58k95rljkkfa6oglphbiufnadatc.apps.googleusercontent.com"
    private val clientSecret = "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"

    suspend fun login(provider: LoginAPI.Provider, token: String) =
        LoginAPI.loginRetrofit(getAccessTokenBaseUrl).getLoginToken(provider.provider, token)

    fun getAccessToken(authCode: String) {
        LoginAPI.loginRetrofit(getAccessTokenBaseUrl).getAccessToken(
            request = LoginGoogleRequestModel(
                grant_type = "authorization_code",
                client_id = clientID,
                client_secret = clientSecret,
                code = authCode.orEmpty()
            )
        ).enqueue(object : Callback<LoginGoogleResponseModel> {
            override fun onResponse(
                call: Call<LoginGoogleResponseModel>,
                response: Response<LoginGoogleResponseModel>
            ) {
                if (response.isSuccessful) {
                    val accessToken = response.body()?.access_token.orEmpty()
                    Log.d(TAG, "accessToken: $accessToken")
                    sendAccessToken(accessToken)
                }
            }

            override fun onFailure(call: Call<LoginGoogleResponseModel>, t: Throwable) {
                Log.e(TAG, "getOnFailure: ", t.fillInStackTrace())
            }
        })
    }

    fun sendAccessToken(accessToken: String) {
        LoginAPI.loginRetrofit(sendAccessTokenBaseUrl).sendAccessToken(
            accessToken = SendAccessTokenModel(accessToken)
        ).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "sendOnResponse: ${response.body()}")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(TAG, "sendOnFailure: ${t.fillInStackTrace()}")
            }
        })
    }

    companion object {
        const val TAG = "LoginRepository"
    }
}