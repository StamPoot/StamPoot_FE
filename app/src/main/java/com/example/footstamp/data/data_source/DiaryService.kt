package com.example.footstamp.data.data_source

import com.example.footstamp.data.dto.request.reply.ReportReqDTO
import com.example.footstamp.data.dto.response.diary.DiaryDetailDTO
import com.example.footstamp.data.dto.response.diary.DiaryListDTO
import com.example.footstamp.data.model.Diary
import com.example.footstamp.ui.base.BaseService
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface DiaryService : BaseService {

    // 일기 작성
    @POST("/diary")
    suspend fun diaryWrite(
        @Query("authUser") userId: String,
        @Query("req") diary: Diary
    ): Response<Unit>

    // 일기 신고
    @POST("/diary/{id}/report")
    suspend fun diaryReport(
        @Path("id") id: String,
        @Query("authUser") userId: String,
        @Body reportReqDTO: ReportReqDTO
    ): Response<Unit>

    // 일기 비공개
    @POST("/diary/{id}/public")
    suspend fun diaryTransPublic(
        @Query("authUser") userId: String,
        @Path("id") id: String
    ): Response<Unit>

    // 좋아요 / 좋아요 취소
    @POST("/diary/{id}/like")
    suspend fun diaryTransLike(
        @Query("authUser") userId: String,
        @Path("id") id: String
    ): Response<Unit>

    // 앨범 > 일기 상세보기 조회
    @GET("/diary/{id}")
    suspend fun diaryDetail(
        @Query("authUser") userId: String,
        @Path("id") id: String
    ): Response<DiaryDetailDTO>

    // 일기 삭제
    @DELETE("/diary/{id}")
    suspend fun diaryDelete(
        @Path("id") id: String,
        @Query("authUser") userId: String
    ): Response<Unit>

    // 지도, 앨범 > 내 다이어리 목록 조회
    @GET("/diary/my")
    suspend fun diaryList(
        @Query("authUser") userId: String
    ): Response<DiaryListDTO>
}