package com.example.footstamp.data.dto.response.user

import com.google.gson.annotations.SerializedName

data class NotificationDTO(
    @SerializedName("profileDto")
    val profileDto: UserProfileDTO,
    @SerializedName("content")
    val content: String,
    @SerializedName("dateTime")
    val dateTime: String
)