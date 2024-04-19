package com.example.footstamp.data.repository

import com.example.footstamp.data.data_source.BoardService
import com.example.footstamp.data.data_source.ReplyService
import com.example.footstamp.data.dto.request.reply.CreateReplyReqDTO
import com.example.footstamp.data.dto.request.reply.ReportReqDTO
import com.example.footstamp.data.dto.response.diary.DiaryDTO
import com.example.footstamp.data.model.Diary
import com.example.footstamp.data.util.TokenManager
import com.example.footstamp.ui.base.BaseRepository
import retrofit2.Response
import javax.inject.Inject

class BoardRepository @Inject constructor(
    private val tokenManager: TokenManager,
    private val boardService: BoardService,
    private val replyService: ReplyService
) : BaseRepository() {

    suspend fun getBoardDiaryList(): Response<List<DiaryDTO>> {
        return boardService.boardFeeds(1)
    }

    suspend fun addReply(id: String, content: String) {
        replyService.replyWrite(id, tokenManager.accessToken!!, CreateReplyReqDTO(content))
    }

    // id는 게시글 id 인지 댓글 id 인지, 일기 신고, 좋아요 API 수정 요청
    suspend fun deleteReply(id: String) {
        replyService.replyDelete(id, tokenManager.accessToken!!)
    }
}