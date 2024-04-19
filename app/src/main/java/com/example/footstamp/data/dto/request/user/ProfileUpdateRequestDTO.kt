package com.example.footstamp.data.dto.request.user

import com.google.gson.annotations.SerializedName

data class ProfileUpdateRequestDTO(
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("picture")
    val picture: String?,
    @SerializedName("sentence")
    val sentence: String?
)