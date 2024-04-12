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

    suspend fun writeDiary(diary: Diary) {
        insertDiary(diary)
        diaryService.diaryWrite(tokenManager.accessToken!!, diary)
    }

    // 생성
    suspend fun insertDiaries(diaryList: List<Diary>) = diaryDao.insertDiaries(diaryList)

    suspend fun insertDiary(diary: Diary) = diaryDao.insertDiaries(diary)

    // 삭제
    suspend fun deleteDiary(id: Long) = diaryDao.deleteDiary(id)

    suspend fun deleteAll() = diaryDao.deleteAll()

    // 업데이트

    suspend fun updateDiary(
        id: Long,
        title: String?,
        message: String?,
        date: LocalDateTime?,
        isShared: Boolean?,
        location: SeoulLocation?,
        photoList: List<String>?,
        thumbnailIndex: Int?,
    ) {
        title?.let { diaryDao.updateTitle(id, it) }
        message?.let { diaryDao.updateMessage(id, it) }
        date?.let { diaryDao.updateDate(id, it) }
        isShared?.let { diaryDao.updateIsShared(id, it) }
        location?.let { diaryDao.updateLocation(id, it) }
        photoList?.let { diaryDao.updatePhotoList(id, it) }
        thumbnailIndex?.let { diaryDao.updateThumbnailIndex(id, it) }
    }

    // 탐색
    suspend fun getAll(): Flow<List<Diary>> =
        diaryDao.getAll().flowOn(Dispatchers.IO).conflate()

    suspend fun getDiary(id: Long): Diary =
        diaryDao.getDiary(id)

    suspend fun getDiaryByLocation(location: SeoulLocation): List<Diary> =
        diaryDao.getDiaryByLocation(location)
}