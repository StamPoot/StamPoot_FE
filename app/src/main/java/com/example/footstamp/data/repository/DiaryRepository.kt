package com.example.footstamp.data.repository

import android.content.Context
import android.graphics.Bitmap
import com.example.footstamp.data.dao.DiaryDao
import com.example.footstamp.data.data_source.DiaryService
import com.example.footstamp.data.dto.response.diary.DiaryDTO
import com.example.footstamp.data.model.Diary
import com.example.footstamp.data.util.Formatter
import com.example.footstamp.data.util.SeoulLocation
import com.example.footstamp.data.util.TokenManager
import com.example.footstamp.ui.base.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDateTime
import javax.inject.Inject

class DiaryRepository @Inject constructor(
    private val tokenManager: TokenManager,
    private val diaryDao: DiaryDao,
    private val diaryService: DiaryService
) : BaseRepository() {

    suspend fun getDiaries() {
        diaryService.diaryList(tokenManager.accessToken!!).let { response ->
            if (response.isSuccessful) {
                val responseBody = response.body()!!
                val responseDiaries = mutableListOf<Diary>()

                // 지역 별로 데이터가 수신되기 때문에 이중 map 사용
                responseBody.diaries.values.map { diaries ->
                    diaries.map { diaryDTO ->
                        val photoBitmaps = if (diaryDTO.photos.isEmpty()) emptyList()
                        else diaryDTO.photos.map { Formatter.fetchImageBitmap(it)!! }

                        diaryDTOToDiary(
                            diaryDTO = diaryDTO,
                            photoBitmaps = photoBitmaps,
                            uid = responseBody.userId.toString()
                        )
                    }.let { responseDiaries.addAll(it) }
                }
                // DB 내의 일기들 최신화
                deleteAllDao()
                insertDiariesDao(responseDiaries)
            }
        }
    }

    suspend fun writeDiary(diary: Diary, context: Context): Boolean {
        diaryService.diaryWrite(
            token = tokenManager.accessToken!!,
            title = Formatter.createPartFromString(diary.title),
            content = Formatter.createPartFromString(diary.message),
            date = Formatter.createPartFromString(Formatter.localTimeToDiaryString(diary.date)),
            location = Formatter.createPartFromString(diary.location.toString()),
            thumbnailNo = Formatter.createPartFromString(diary.thumbnail.toString()),
            photos = diary.photoBitmapStrings.map { photoString ->
                val photo = Formatter.convertStringToBitmap(photoString)
                Formatter.convertBitmapToFile("photos", photo, context)
            }
        ).let { response ->
            if (response.isSuccessful) {
                val id = response.body()!!.removeRange(0..9).toLong()
                insertDiaryDao(diary.apply { insertId(id) })
                return true
            }
        }
        return false
    }

    suspend fun readDiary(diaryId: String): Diary? {
        diaryService.diaryDetail(tokenManager.accessToken!!, diaryId).let {
            if (it.isSuccessful) {
                val response = it.body()!!
                return Diary(
                    title = response.title,
                    message = response.content,
                    date = Formatter.longToLocalDateTime(response.date.toLong()),
                    location = response.location
                )
            }
        }
        return null
    }

    // id 수정 필요
    suspend fun deleteDiary(diary: Diary): Boolean {
        diaryService.diaryDelete(diary.id.toString(), tokenManager.accessToken!!).let { response ->
            if (response.isSuccessful) {
                deleteDiaryDao(diary.id)
                return true
            }
        }
        return false
    }

    // 일기 수정 API 요청
    suspend fun updateDiary(diary: Diary, context: Context): Boolean {
        diaryService.diaryEdit(
            token = tokenManager.accessToken!!,
            title = Formatter.createPartFromString(diary.title),
            content = Formatter.createPartFromString(diary.message),
            date = Formatter.createPartFromString(Formatter.localTimeToDiaryString(diary.date)),
            location = Formatter.createPartFromString(diary.location.toString()),
            thumbnailNo = Formatter.createPartFromString(diary.thumbnail.toString()),
            photos = diary.photoBitmapStrings.map { photoString ->
                val photo = Formatter.convertStringToBitmap(photoString)
                Formatter.convertBitmapToFile("photos", photo, context)
            },
            id = diary.id.toString()
        ).let {
            if (it.isSuccessful) {
                getDiaries()
                return true
            }
            return false
        }
    }

    // 일기 공유 상태 수정
    suspend fun shareDiary(diaryId: String) {
        diaryService.diaryTransPublic(
            token = tokenManager.accessToken!!,
            id = diaryId
        ).let {
            if (it.isSuccessful) {
                getDiaries()
            }
        }
    }

    // dao Database

    suspend fun insertDiaryDao(diary: Diary) = diaryDao.insertDiary(diary)

    suspend fun insertDiariesDao(diaries: List<Diary>) = diaryDao.insertDiaries(diaries)

    suspend fun deleteDiaryDao(id: Long) = diaryDao.deleteDiary(id)

    suspend fun deleteAllDao() = diaryDao.deleteAll()

    suspend fun updateDiaryDao(
        id: Long,
        title: String? = null,
        message: String? = null,
        date: LocalDateTime? = null,
        isShared: Boolean? = null,
        location: SeoulLocation? = null,
        photoList: List<String>? = null,
        thumbnailIndex: Int? = null,
    ) {
        title?.let { diaryDao.updateTitle(id, it) }
        message?.let { diaryDao.updateMessage(id, it) }
        date?.let { diaryDao.updateDate(id, it) }
        isShared?.let { diaryDao.updateIsShared(id, it) }
        location?.let { diaryDao.updateLocation(id, it) }
        photoList?.let { diaryDao.updatePhotoList(id, it) }
        thumbnailIndex?.let { diaryDao.updateThumbnailIndex(id, it) }
    }

    suspend fun getAllDao(): List<Diary> =
        diaryDao.getAll()

    suspend fun getDiaryDao(id: Long): Diary =
        diaryDao.getDiary(id)

    suspend fun getDiaryByLocationDao(location: SeoulLocation): List<Diary> =
        diaryDao.getDiaryByLocation(location)

    private fun diaryDTOToDiary(
        diaryDTO: DiaryDTO,
        photoBitmaps: List<Bitmap>,
        uid: String
    ): Diary {

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
            uid = uid
        ).apply { insertId(diaryDTO.id.toLong()) }
    }
}