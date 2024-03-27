package com.example.footstamp.data.dto.login

import com.google.gson.annotations.SerializedName

data class AuthToken(
    @SerializedName("auth")
    val auth: String,
    @SerializedName("refresh")
    val refresh: String
)