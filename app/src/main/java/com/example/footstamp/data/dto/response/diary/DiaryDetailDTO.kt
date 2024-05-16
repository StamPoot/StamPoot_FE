package com.example.footstamp.data.dto.response.diary

import com.example.footstamp.data.dto.response.reply.ReplyDTO
import com.example.footstamp.data.util.SeoulLocation
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
    val photos: String,
    @SerializedName("likes")
    val likes: Int,
    @SerializedName("isLiked")
    val isLiked: Boolean,
    @SerializedName("id")
    val id: Int,
    @SerializedName("replyList")
    val replyList: List<ReplyDTO>,
)