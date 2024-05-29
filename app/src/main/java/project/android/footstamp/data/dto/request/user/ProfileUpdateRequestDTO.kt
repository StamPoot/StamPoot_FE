package project.android.footstamp.data.dto.request.user

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part

data class ProfileUpdateRequestDTO(
    @SerializedName("nickname")
    @Part
    val nickname: RequestBody,
    @SerializedName("picture")
    @Part
    val picture: MultipartBody.Part?,
    @SerializedName("sentence")
    @Part
    val sentence: RequestBody?,
)