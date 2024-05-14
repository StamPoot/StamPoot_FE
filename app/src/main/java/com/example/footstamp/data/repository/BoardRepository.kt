package com.example.footstamp.data.repository

import android.graphics.Bitmap
import com.example.footstamp.data.data_source.BoardService
import com.example.footstamp.data.data_source.ReplyService
import com.example.footstamp.data.dto.request.reply.CreateReplyReqDTO
import com.example.footstamp.data.dto.response.diary.DiaryDTO
import com.example.footstamp.data.model.Diary
import com.example.footstamp.data.util.Formatter
import com.example.footstamp.data.util.TokenManager
import com.example.footstamp.ui.base.BaseRepository
import javax.inject.Inject

class BoardRepository @Inject constructor(
    private val tokenManager: TokenManager,
    private val boardService: BoardService,
    private val replyService: ReplyService
) : BaseRepository() {

    suspend fun getBoardDiaryList(sortType: BoardSortType): List<Diary>? {
        boardService.boardFeeds(sortType.sortCode).let { response ->
            if (response.isSuccessful) {
                val responseBody = response.body()!!

                responseBody.map { diaryDTO ->
                    val photoBitmaps = diaryDTO.photos.map { Formatter.fetchImageBitmap(it)!! }

                    diaryDTOToDiary(
                        diaryDTO = diaryDTO,
                        photoBitmaps = photoBitmaps
                    )
                }.let { return it }
            }
            return null
        }
    }

    suspend fun addReply(id: String, content: String) {
        replyService.replyWrite(id, tokenManager.accessToken!!, CreateReplyReqDTO(content))
    }

    // id는 게시글 id 인지 댓글 id 인지, 일기 신고, 좋아요 API 수정 요청
    suspend fun deleteReply(id: String) {
        replyService.replyDelete(id, tokenManager.accessToken!!)
    }

    suspend fun likeDiary(id: String): Int? {
        boardService.diaryLikes(tokenManager.accessToken!!, id).let { response ->
            return if (response.isSuccessful) {
                val responseBody = response.body()!!
                responseBody.substring(8).toInt()
            } else null
        }
    }

    private fun diaryDTOToDiary(diaryDTO: DiaryDTO, photoBitmaps: List<Bitmap>): Diary {

        return Diary(
            title = diaryDTO.title,
            date = Formatter.dateStringToLocalDateTime(diaryDTO.date),
            message = diaryDTO.content,
            isShared = diaryDTO.isPublic,
            location = diaryDTO.location,
            photoBitmapStrings = photoBitmaps.map {
                Formatter.convertBitmapToString(it)
            },
            thumbnail = diaryDTO.thumbnailNo,
            likes = diaryDTO.likes
        ).apply { insertId(diaryDTO.id.toLong()) }
    }
}

enum class BoardSortType(val sortCode: Int) {
    RECENT(1),
    LIKE(2)
}