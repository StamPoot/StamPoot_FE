package project.android.footstamp.data.data_source

import project.android.footstamp.data.dto.response.user.NotificationDTO
import project.android.footstamp.data.dto.response.user.UserProfileDTO
import project.android.footstamp.ui.base.BaseService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.Part

interface UserService : BaseService {

    // 프로필 조회
    @GET("/profile")
    suspend fun profileGet(
        @Header("token") token: String,
    ): Response<UserProfileDTO>

    // 프로필 수정 요청
    @Multipart
    @PATCH("/profile")
    suspend fun profileEdit(
        @Header("token") token: String,
        @Part("nickname") nickname: RequestBody,
        @Part picture: MultipartBody.Part?,
        @Part("sentence") sentence: RequestBody?,
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
    ): Response<List<NotificationDTO>>
}