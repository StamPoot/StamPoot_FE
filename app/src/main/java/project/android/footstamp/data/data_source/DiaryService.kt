package project.android.footstamp.data.data_source

import project.android.footstamp.data.dto.response.diary.DiaryDetailDTO
import project.android.footstamp.data.dto.response.diary.DiaryListDTO
import project.android.footstamp.ui.base.BaseService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

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