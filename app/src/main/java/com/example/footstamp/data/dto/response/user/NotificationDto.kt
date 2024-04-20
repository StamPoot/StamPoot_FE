package com.example.footstamp.data.dto.response.user

import com.google.gson.annotations.SerializedName

data class NotificationDto(
    @SerializedName("profileDto")
    val profileDto: UserProfileDto,
    @SerializedName("content")
    val content: String,
    @SerializedName("dateTime")
    val dateTime: String
)