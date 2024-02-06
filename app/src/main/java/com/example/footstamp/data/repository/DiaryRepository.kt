package com.example.footstamp.data.repository

import com.example.footstamp.data.dao.DiaryDao
import com.example.footstamp.data.model.Diary
import com.example.footstamp.data.util.SeoulLocation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDateTime
import javax.inject.Inject

class DiaryRepository @Inject constructor(private val diaryDao: DiaryDao) {

    val diaries: Flow<List<Diary>> = diaryDao.getAll().flowOn(Dispatchers.IO).conflate()

    // 생성
    suspend fun insertDiaries(diaryList: List<Diary>) = diaryDao.insertDiaries(diaryList)


    suspend fun insertDiary(diary: Diary) = diaryDao.insertDiaries(diary)


    // 삭제
    suspend fun deleteDiary(id: Long) = diaryDao.deleteDiary(id)


    suspend fun deleteAll() =
        diaryDao.deleteAll()


    // 업데이트
    suspend fun updateTitle(id: Long, title: String) =
        diaryDao.updateTitle(id, title)


    suspend fun updateMessage(id: Long, message: String) =
        diaryDao.updateMessage(id, message)


    suspend fun updateDate(id: Long, date: LocalDateTime) =
        diaryDao.updateDate(id, date)


    suspend fun updateIsShared(id: Long, title: String) =
        diaryDao.updateTitle(id, title)


    suspend fun updateLocation(id: Long, location: SeoulLocation) =
        diaryDao.updateLocation(id, location)


    suspend fun updatePhotoUrl(id: Long, photoUrls: List<String>) =
        diaryDao.updatePhotoUrl(id, photoUrls)


    suspend fun updateThumbnail(id: Long, thumbnail: Int) =
        diaryDao.updateThumbnail(id, thumbnail)


    // 탐색
    suspend fun getAll(): Flow<List<Diary>> =
        diaryDao.getAll().flowOn(Dispatchers.IO).conflate()


    suspend fun getDiary(id: Long): Diary =
        diaryDao.getDiary(id)

}