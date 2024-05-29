package project.android.footstamp.data.dto.response.diary

import project.android.footstamp.data.util.SeoulLocation
import com.google.gson.annotations.SerializedName

data class DiaryListDTO(
    @SerializedName("diaries")
    val diaries: Map<SeoulLocation, List<DiaryDTO>>,
    @SerializedName("userId")
    val userId: Int
)