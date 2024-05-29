package project.android.footstamp.data.repository

import android.content.Context
import android.graphics.Bitmap
import project.android.footstamp.data.dao.DiaryDao
import project.android.footstamp.data.data_source.DiaryService
import project.android.footstamp.data.dto.response.diary.DiaryDTO
import project.android.footstamp.data.model.Diary
import project.android.footstamp.data.util.Formatter
import project.android.footstamp.data.util.SeoulLocation
import project.android.footstamp.data.util.TokenManager
import project.android.footstamp.ui.base.BaseRepository
import java.time.LocalDateTime
import javax.inject.Inject

class DiaryRepository @Inject constructor(
    private val tokenManager: TokenManager,
    private val diaryDao: DiaryDao,
    private val diaryService: DiaryService
) : BaseRepository() {

    suspend fun fetchDiaries(): List<Diary>? {
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
                    }.let {
                        responseDiaries.addAll(it)
                        return it
                    }
                }
                // DB 내의 일기들 최신화
                deleteAllDao()
                insertDiariesDao(responseDiaries)
            }
        }
        return null
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

    suspend fun deleteDiary(diaryId: String): Boolean {
        diaryService.diaryDelete(diaryId, tokenManager.accessToken!!).let { response ->
            if (response.isSuccessful) {
                deleteDiaryDao(diaryId.toLong())
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
                fetchDiaries()
                return true
            }
            return false
        }
    }

    // 일기 공유 상태 수정
    suspend fun shareDiary(diaryId: String): Boolean {
        diaryService.diaryTransPublic(
            token = tokenManager.accessToken!!,
            id = diaryId
        ).let {
            if (it.isSuccessful) {
                return true
            }
        }
        return false
    }

    // dao Database DB 함수만 구현

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

    // Backend 에서 사용하는 모델을 앱 내의 Diary 모델로 변환
    private fun diaryDTOToDiary(
        diaryDTO: DiaryDTO,
        photoBitmaps: List<Bitmap>,
        uid: String
    ): Diary {

        return Diary(
            title = diaryDTO.title,
            date = Formatter.stringToDateTime(diaryDTO.date.substring(0, 10)),
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