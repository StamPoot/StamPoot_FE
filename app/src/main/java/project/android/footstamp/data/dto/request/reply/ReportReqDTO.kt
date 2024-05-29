package project.android.footstamp.data.dto.request.reply

import com.google.gson.annotations.SerializedName

data class ReportReqDTO(
    @SerializedName("reason")
    val reason: String
)