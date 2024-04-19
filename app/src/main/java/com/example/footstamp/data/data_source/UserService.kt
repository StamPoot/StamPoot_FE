package com.example.footstamp.data.data_source

import com.example.footstamp.data.dto.request.user.ProfileUpdateRequestDTO
import com.example.footstamp.data.dto.response.user.UserProfileDto
import com.example.footstamp.ui.base.BaseService
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Query

interface UserService : BaseService {

    // 프로필 조회
    @GET("/profile")
    suspend fun profileGet(
        @Header("token") token: String,
        ): Response<UserProfileDto>

    // 프로필 수정 요청
    @PATCH("/profile")
    suspend fun profileEdit(
        @Header("token") token: String,
        @Body profileUpdateRequest: ProfileUpdateRequestDTO
    ): Response<Unit>

    // 회원 탈퇴
    @DELETE("/user")
    suspend fun profileDelete(
        @Header("token") token: String,
    ): Response<Unit>

    // 알림 박스 조회
    @GET("/notification")
    suspend fun profileNotification(
        @Header("token") token: String,
    )
}