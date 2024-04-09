package com.example.footstamp.data.data_source

import com.example.footstamp.data.dto.request.reply.CreateReplyReqDTO
import com.example.footstamp.data.dto.request.reply.ReportReqDTO
import com.example.footstamp.ui.base.BaseService
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ReplyService : BaseService {

    // 댓글 신고
    @POST("/reply/{id}/report")
    suspend fun replyReport(
        @Path("id") id: String,
        @Query("authUser") userId: String,
        @Body reportReqDTO: ReportReqDTO
    ): Response<Unit>

    // 댓글 작성
    @POST("/diary/{id}/reply")
    suspend fun replyWrite(
        @Path("id") id: String,
        @Query("authUser") userId: String,
        @Body createReplyReqDTO: CreateReplyReqDTO
    ): Response<Unit>

    // 댓글 삭제
    @DELETE("/reply/{id}")
    suspend fun replyDelete(
        @Path("id") id: String,
        @Query("authUser") userId: String,
    ): Response<Unit>
}