package project.android.footstamp.data.repository

import android.graphics.Bitmap
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import project.android.footstamp.data.data_source.BoardService
import project.android.footstamp.data.data_source.DiaryService
import project.android.footstamp.data.data_source.ReplyService
import project.android.footstamp.data.dto.request.reply.CreateReplyReqDTO
import project.android.footstamp.data.dto.request.reply.ReportReqDTO
import project.android.footstamp.data.dto.response.diary.DiaryDTO
import project.android.footstamp.data.model.Comment
import project.android.footstamp.data.model.Diary
import project.android.footstamp.data.model.Profile
import project.android.footstamp.data.util.Formatter
import project.android.footstamp.data.util.TokenManager
import project.android.footstamp.ui.base.BaseRepository
import javax.inject.Inject

class BoardRepository @Inject constructor(
    private val tokenManager: TokenManager,
    private val boardService: BoardService,
    private val replyService: ReplyService,
    private val diaryService: DiaryService
) : BaseRepository() {
    private val accessToken = tokenManager.accessToken

    suspend fun fetchBoardDiaryList(sortType: BoardSortType): List<Diary>? {
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

    suspend fun fetchDiaryDetail(id: String): Triple<Diary, Profile, List<Comment>> {
        diaryService.diaryDetail(accessToken.first()!!, id).let { response ->
            val responseBody = response.body()!!

            val diary = Diary(
                title = responseBody.title,
                date = Formatter.stringToDateTime(responseBody.date),
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
                    date = Formatter.dateTimeToFormedString(replyDTO.date),
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

    suspend fun fetchAddReply(id: String, content: String): Boolean {
        replyService.replyWrite(
            id, accessToken.first()!!, CreateReplyReqDTO(content)
        ).let { response ->
            if (response.isSuccessful) return true
        }
        return false
    }

    suspend fun likeDiary(id: String): Int? {
        boardService.diaryLikes(accessToken.first()!!, id).let { response ->
            return if (response.isSuccessful) {
                val responseBody = response.body()!!
                responseBody.substring(8).toInt()
            } else null
        }
    }

    suspend fun reportDiary(id: String, reason: String): Boolean {
        val reportReqDTO = ReportReqDTO(reason = reason)

        boardService.diaryReport(id, accessToken.first()!!, reportReqDTO).let { response ->
            return response.isSuccessful
        }
    }

    suspend fun reportReply(id: String, reason: String): Boolean {
        val reportReqDTO = ReportReqDTO(reason = reason)

        boardService.replyReport(id, accessToken.first()!!, reportReqDTO).let { response ->
            return response.isSuccessful
        }
    }

    suspend fun deleteReply(id: String): Boolean {
        replyService.replyDelete(id, accessToken.first()!!).let { response ->
            return response.isSuccessful
        }
    }

    private fun diaryDTOToDiary(diaryDTO: DiaryDTO, photoBitmaps: List<Bitmap>): Diary {

        return Diary(
            title = diaryDTO.title,
            date = Formatter.stringToDateTime(diaryDTO.date),
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