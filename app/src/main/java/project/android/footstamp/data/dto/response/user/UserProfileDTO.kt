package project.android.footstamp.data.dto.response.user

import com.google.gson.annotations.SerializedName

data class UserProfileDTO(
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("profileImg")
    val profileImg: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("sentence")
    val sentence: String?,
    )