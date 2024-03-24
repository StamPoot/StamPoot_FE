package com.example.footstamp.data.dto

import com.google.gson.annotations.SerializedName

data class AuthToken(
    @SerializedName("auth")
    val auth: String,
    @SerializedName("refresh")
    val refresh: String
)