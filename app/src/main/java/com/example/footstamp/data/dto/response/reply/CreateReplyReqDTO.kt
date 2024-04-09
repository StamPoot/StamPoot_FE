package com.example.footstamp.data.dto.response.reply

import com.google.gson.annotations.SerializedName

data class CreateReplyReqDTO(
    @SerializedName("content")
    val content: String
)