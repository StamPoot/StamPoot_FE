package com.example.footstamp.data.repository

import com.example.footstamp.data.dao.DiaryDao
import com.example.footstamp.data.util.Diary
import com.example.footstamp.data.util.SeoulLocation
import java.util.Date

class DiaryRepository(private val diaryDao: DiaryDao) {

    val diarys: List<Diary> = diaryDao.getAll()

    // 생성
    suspend fun insertDiarys(diaryList: List<Diary>) {
        diaryDao.insertDiarys(diaryList)
    }

    suspend fun insertDiary(diary: Diary) {
        diaryDao.insertDiary(diary)
    }

    // 삭제
    suspend fun deleteDiary(id: Long) {
        diaryDao.deleteDiary(id)
    }

    suspend fun deleteAll() {
        diaryDao.deleteAll()
    }

    // 업데이트
    suspend fun updateTitle(id: Long, title: String) {
        diaryDao.updateTitle(id, title)
    }

    suspend fun updateMessage(id: Long, message: String) {
        diaryDao.updateMessage(id, message)
    }

    suspend fun updateDate(id: Long, date: Date) {
        diaryDao.updateDate(id, date)
    }

    suspend fun updateIsShared(id: Long, title: String) {
        diaryDao.updateTitle(id, title)
    }

    suspend fun updateLocation(id: Long, location: SeoulLocation) {
        diaryDao.updateLocation(id, location)
    }

    suspend fun updatePhotoUrl(id: Long, photoUrls: List<String>) {
        diaryDao.updatePhotoUrl(id, photoUrls)
    }

    suspend fun updateThumbnail(id: Long, thumbnail: Int) {
        diaryDao.updateThumbnail(id, thumbnail)
    }

    // 탐색
    suspend fun getAll() {
        diaryDao.getAll()
    }

    suspend fun getDiary(id: Long) {
        diaryDao.getDiary(id)
    }
}