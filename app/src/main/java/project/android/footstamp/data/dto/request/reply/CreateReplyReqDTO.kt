package project.android.footstamp.data.dto.request.reply

import com.google.gson.annotations.SerializedName

data class CreateReplyReqDTO(
    @SerializedName("content")
    val content: String
)