package com.example.footstamp.data.data_source

import com.example.footstamp.data.dto.response.diary.DiaryDTO
import com.example.footstamp.ui.base.BaseService
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BoardService : BaseService {

    // 공개된 일기 조회
    @GET("/board/feeds")
    suspend fun boardFeeds(
        // sort 1: 최신, sort 2: 좋아요 순
        @Query("sort") sort: Int
    ): Response<List<DiaryDTO>>
}