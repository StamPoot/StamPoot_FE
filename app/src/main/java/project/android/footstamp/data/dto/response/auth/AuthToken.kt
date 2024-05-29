package project.android.footstamp.data.dto.response.auth

import com.google.gson.annotations.SerializedName

data class AuthToken(
    @SerializedName("auth")
    val auth: String,
    @SerializedName("refresh")
    val refresh: String
)