package com.example.footstamp.data.model

import android.graphics.Bitmap

data class Comment(
    val content: String,
    val date: String,
    val writerId: Int,
    val nickname: String,
    val profileImage: Bitmap?,
    val sentence: String,
    val isMine: Boolean,
    val id: Long
)