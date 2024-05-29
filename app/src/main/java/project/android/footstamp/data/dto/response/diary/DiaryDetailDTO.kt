package project.android.footstamp.data.dto.response.diary

import project.android.footstamp.data.dto.response.reply.ReplyDTO
import project.android.footstamp.data.dto.response.reply.UserProfileDTO
import project.android.footstamp.data.util.SeoulLocation
import com.google.gson.annotations.SerializedName

data class DiaryDetailDTO(
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("location")
    val location: SeoulLocation,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("thumbnailNo")
    val thumbnailNo: Int,
    @SerializedName("photos")
    val photos: List<String>,
    @SerializedName("isPublic")
    val isPublic: Boolean,
    @SerializedName("likes")
    val likes: Int,
    @SerializedName("isLiked")
    val isLiked: Boolean,
    @SerializedName("id")
    val id: Int,
    @SerializedName("writerInfo")
    val writerInfo: UserProfileDTO,
    @SerializedName("replyList")
    val replyList: List<ReplyDTO>,
)