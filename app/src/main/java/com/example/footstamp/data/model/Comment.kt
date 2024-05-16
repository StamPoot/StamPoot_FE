package com.example.footstamp.data.model

data class Comment(
    val content: String,
    val date: String,
    val writerId: Int,
    val isMine: Boolean,
    val id: Long
)