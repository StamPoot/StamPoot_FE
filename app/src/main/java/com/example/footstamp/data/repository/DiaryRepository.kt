package com.example.footstamp.data.repository

import com.example.footstamp.data.dao.DiaryDao
import com.example.footstamp.data.data_source.DiaryService
import com.example.footstamp.data.model.Diary
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

    val diaries: Flow<List<Diary>> = diaryDao.getAll().flowOn(Dispatchers.IO).conflate()

    suspend fun getDiaries() {
        diaryDao.getAll()
        diaryService.diaryList(tokenManager.accessToken!!)
    }

    suspend fun writeDiary(diary: Diary) {
        diaryService.diaryWrite(tokenManager.accessToken!!, diary)
        insertDiaryDao(diary)
    }

    suspend fun readDiary(diary: Diary) {
        diaryService.diaryDetail(tokenManager.accessToken!!, diary.id.toString())
    }

    // id 수정 필요
    suspend fun deleteDiary(diary: Diary) {
        diaryService.diaryDelete(diary.id.toString(), tokenManager.accessToken!!)
        deleteDiaryDao(diary.id)
    }

    // 일기 수정 API 요청
    suspend fun updateDiary(
        diary: Diary,
        title: String? = null,
        message: String? = null,
        date: LocalDateTime? = null,
        isShared: Boolean? = null,
        location: SeoulLocation? = null,
        photoList: List<String>? = null,
        thumbnailIndex: Int? = null,
    ) {
        updateDiaryDao(diary.id)
    }

    // dao Database

    suspend fun insertDiaryDao(diary: Diary) = diaryDao.insertDiaries(diary)

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

    suspend fun getAllDao(): Flow<List<Diary>> =
        diaryDao.getAll().flowOn(Dispatchers.IO).conflate()

    suspend fun getDiaryDao(id: Long): Diary =
        diaryDao.getDiary(id)

    suspend fun getDiaryByLocationDao(location: SeoulLocation): List<Diary> =
        diaryDao.getDiaryByLocation(location)
}