package com.example.footstamp.data.repository

import android.graphics.Bitmap
import com.example.footstamp.data.data_source.BoardService
import com.example.footstamp.data.data_source.DiaryService
import com.example.footstamp.data.data_source.ReplyService
import com.example.footstamp.data.dto.request.reply.CreateReplyReqDTO
import com.example.footstamp.data.dto.response.diary.DiaryDTO
import com.example.footstamp.data.model.Comment
import com.example.footstamp.data.model.Diary
import com.example.footstamp.data.model.Profile
import com.example.footstamp.data.util.Formatter
import com.example.footstamp.data.util.TokenManager
import com.example.footstamp.ui.base.BaseRepository
import javax.inject.Inject

class BoardRepository @Inject constructor(
    private val tokenManager: TokenManager,
    private val boardService: BoardService,
    private val replyService: ReplyService,
    private val diaryService: DiaryService
) : BaseRepository() {

    suspend fun getBoardDiaryList(sortType: BoardSortType): List<Diary>? {
        boardService.boardFeeds(sortType.sortCode).let { response ->
            if (response.isSuccessful) {
                val responseBody = response.body()!!

                responseBody.map { diaryDTO ->
                    val photoBitmaps = diaryDTO.photos.map { Formatter.fetchImageBitmap(it)!! }

                    diaryDTOToDiary(
                        diaryDTO = diaryDTO, photoBitmaps = photoBitmaps
                    )
                }.let { return it }
            }
            return null
        }
    }

    suspend fun getDiaryDetail(id: String): Triple<Diary, Profile, List<Comment>> {
        diaryService.diaryDetail(tokenManager.accessToken!!, id).let { response ->
            val responseBody = response.body()!!

            val diary = Diary(
                title = responseBody.title,
                date = Formatter.dateStringToLocalDateTime(responseBody.date),
                message = responseBody.content,
                isShared = responseBody.isPublic,
                location = responseBody.location,
                photoBitmapStrings = responseBody.photos.map { photo ->
                    Formatter.fetchImageBitmap(photo)!!.let { Formatter.convertBitmapToString(it) }
                },
                thumbnail = responseBody.thumbnailNo,
                uid = "0",
                likes = responseBody.likes
            ).apply { this.insertId(responseBody.id.toLong()) }
            val profile = Profile(
                nickname = responseBody.writerInfo.nickname,
                image = Formatter.fetchImageBitmap(responseBody.writerInfo.profileImage)?.let {
                    Formatter.convertBitmapToString(it)
                },
                aboutMe = responseBody.writerInfo.sentence
            )
            val commentList = responseBody.replyList.map { replyDTO ->
                Comment(
                    content = replyDTO.content,
                    date = Formatter.dateStringToString(replyDTO.date),
                    writerId = replyDTO.writerId,
                    nickname = replyDTO.writerInfo.nickname,
                    profileImage = Formatter.fetchImageBitmap(replyDTO.writerInfo.profileImage),
                    sentence = replyDTO.writerInfo.sentence,
                    isMine = replyDTO.isMine,
                    id = replyDTO.id.toLong()
                )
            }
            return Triple(diary, profile, commentList)
        }
    }

    suspend fun addReply(id: String, content: String): Boolean {
        replyService.replyWrite(
            id, tokenManager.accessToken!!, CreateReplyReqDTO(content)
        ).let { response ->
            if (response.isSuccessful) return true
        }
        return false
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
    RECENT(1), LIKE(2)
}