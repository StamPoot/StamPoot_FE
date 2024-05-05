package com.example.footstamp.data.data_source

import com.example.footstamp.data.dto.request.reply.ReportReqDTO
import com.example.footstamp.data.dto.response.diary.DiaryDTO
import com.example.footstamp.ui.base.BaseService
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface BoardService : BaseService {

    // 일기 신고
    @POST("/board/{id}/report")
    suspend fun diaryReport(
        @Path("id") id: String,
        @Header("token") token: String,
        @Body reportReqDTO: ReportReqDTO
    ): Response<Unit>

    // 댓글 신고
    @POST("/board/{id}/report")
    suspend fun replyReport(
        @Path("id") id: String,
        @Header("token") token: String,
        @Body reportReqDTO: ReportReqDTO
    ): Response<Unit>

    // 공개된 일기 조회
    @GET("/board/feeds")
    suspend fun boardFeeds(
        // sort 1: 최신, sort 2: 좋아요 순
        @Query("sort") sort: Int
    ): Response<List<DiaryDTO>>

    // 좋아요 / 좋아요 취소
    @POST("/diary/{id}/like")
    suspend fun diaryLikes(
        @Header("token") token: String,
        @Path("id") id: String
    ): Response<Unit>
}