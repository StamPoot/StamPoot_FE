package com.example.footstamp.data.dto.response.diary

import com.google.gson.annotations.SerializedName

data class DiaryListDTO(
    @SerializedName("diaries")
    val diaries: List<DiaryDTO>,
    val userId: Int
)