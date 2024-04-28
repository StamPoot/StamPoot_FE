package com.example.footstamp.data.data_source

import com.example.footstamp.data.dto.response.diary.DiaryDetailDTO
import com.example.footstamp.data.dto.response.diary.DiaryIdDTO
import com.example.footstamp.data.dto.response.diary.DiaryListDTO
import com.example.footstamp.data.model.Diary
import com.example.footstamp.ui.base.BaseService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface DiaryService : BaseService {

    // 일기 작성
    @Multipart
    @POST("/diary")
    suspend fun diaryWrite(
        @Header("token") token: String,
        @Part("title") title: RequestBody,
        @Part("content") content: RequestBody,
        @Part("date") date: RequestBody,
        @Part("location") location: RequestBody,
        @Part("thumbnailNo") thumbnailNo: RequestBody,
        @Part photos: List<MultipartBody.Part>
    ): Response<String>

    // 일기 비공개
    @POST("/diary/{id}/public")
    suspend fun diaryTransPublic(
        @Header("token") token: String,
        @Path("id") id: String
    ): Response<Unit>

    // 좋아요 / 좋아요 취소
    @POST("/diary/{id}/like")
    suspend fun diaryTransLike(
        @Header("token") token: String,
        @Path("id") id: String
    ): Response<Unit>

    // 앨범 > 일기 상세보기 조회
    @GET("/diary/{id}")
    suspend fun diaryDetail(
        @Header("token") token: String,
        @Path("id") id: String
    ): Response<DiaryDetailDTO>

    // 일기 삭제
    @DELETE("/diary/{id}")
    suspend fun diaryDelete(
        @Path("id") id: String,
        @Header("token") token: String,
    ): Response<Unit>

    // 일기 수정
    @Multipart
    @PATCH("/diary/{id}")
    suspend fun diaryEdit(
        @Header("token") token: String,
        @Part("title") title: RequestBody,
        @Part("content") content: RequestBody,
        @Part("date") date: RequestBody,
        @Part("location") location: RequestBody,
        @Part("thumbnailNo") thumbnailNo: RequestBody,
        @Part photos: List<MultipartBody.Part>,
        @Path("id") id: String
    ): Response<Unit>

    // 지도, 앨범 > 내 다이어리 목록 조회
    @GET("/diary/my")
    suspend fun diaryList(
        @Header("token") token: String,
    ): Response<DiaryListDTO>
}