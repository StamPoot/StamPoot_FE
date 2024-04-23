package com.example.footstamp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.footstamp.data.model.Diary
import com.example.footstamp.data.util.SeoulLocation
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
interface DiaryDao {

    // 생성
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDiaries(diaries: List<Diary>)

    @Insert
    fun insertDiary(diary: Diary)

    // 삭제
    @Query("DELETE FROM diaries where id = :id")
    fun deleteDiary(id: Long)

    @Query("DELETE FROM diaries")
    fun deleteAll()

    // 업데이트
    @Query("UPDATE diaries SET diary_title = :title WHERE id = :id")
    fun updateTitle(id: Long, title: String)

    @Query("UPDATE diaries SET diary_message = :message WHERE id = :id")
    fun updateMessage(id: Long, message: String)

    @Query("UPDATE diaries SET diary_date = :date WHERE id = :id")
    fun updateDate(id: Long, date: LocalDateTime)

    @Query("UPDATE diaries SET diary_shared = :isShared WHERE id = :id")
    fun updateIsShared(id: Long, isShared: Boolean)

    @Query("UPDATE diaries SET diary_location = :location WHERE id = :id")
    fun updateLocation(id: Long, location: SeoulLocation)

    @Query("UPDATE diaries SET diary_photo_bitmaps = :photoURLs WHERE id = :id")
    fun updatePhotoList(id: Long, photoURLs: List<String>)

    @Query("UPDATE diaries SET diary_thumbnail= :thumbNail WHERE id = :id")
    fun updateThumbnailIndex(id: Long, thumbNail: Int)

    // 탐색
    @Query("SELECT * FROM diaries")
    fun getAll(): Flow<List<Diary>>

    @Query("SELECT * FROM diaries where id = :id")
    fun getDiary(id: Long): Diary

    @Query("SELECT * FROM diaries where diary_location = :location")
    fun getDiaryByLocation(location: SeoulLocation): List<Diary>
}