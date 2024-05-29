package project.android.footstamp.data.dto.response.reply

import com.google.gson.annotations.SerializedName

data class ReplyDTO(
    @SerializedName("content")
    val content: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("writerId")
    val writerId: Int,
    @SerializedName("isMine")
    val isMine: Boolean,
    @SerializedName("id")
    val id: Int,
    @SerializedName("writerInfo")
    val writerInfo: UserProfileDTO
)