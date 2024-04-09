package com.example.footstamp.data.data_source

import com.example.footstamp.data.dto.request.user.ProfileUpdateRequestDTO
import com.example.footstamp.ui.base.BaseService
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PATCH
import retrofit2.http.Query

interface UserService : BaseService {

    // 프로필 수정 요청
    @PATCH("/profile")
    suspend fun profileEdit(
        @Query("authUser") userId: String,
        @Body profileUpdateRequest: ProfileUpdateRequestDTO
    ): Response<Unit>

    // 회원 탈퇴
    @DELETE("/user")
    suspend fun profileDelete(
        @Query("authUser") userId: String
    ): Response<Unit>
}