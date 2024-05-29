package project.android.footstamp.data.data_source

import project.android.footstamp.data.dto.request.reply.CreateReplyReqDTO
import project.android.footstamp.ui.base.BaseService
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ReplyService : BaseService {

    // 댓글 작성
    @POST("/diary/{id}/reply")
    suspend fun replyWrite(
        @Path("id") id: String,
        @Header("token") token: String,
        @Body createReplyReqDTO: CreateReplyReqDTO
    ): Response<Unit>

    // 댓글 삭제
    @DELETE("/reply/{id}")
    suspend fun replyDelete(
        @Path("id") id: String,
        @Header("token") token: String,
    ): Response<Unit>
}