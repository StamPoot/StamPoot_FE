package com.example.footstamp.data.dto.request.reply

import com.google.gson.annotations.SerializedName

data class CreateReplyReqDTO(
    @SerializedName("content")
    val content: String
)