package com.example.footstamp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.footstamp.data.util.Diary
import com.example.footstamp.data.util.SeoulLocation
import java.util.Date

@Dao
interface DiaryDao {

    // 생성
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDiarys(diarys: List<Diary>)

    @Insert
    fun insertDiary(diary: Diary)

    // 삭제
    @Query("DELETE FROM diarys where id = :id")
    fun deleteDiary(id: Long)

    @Query("DELETE FROM diarys")
    fun deleteAll()

    // 업데이트
    @Query("UPDATE diarys SET diary_title = :title WHERE id = :id")
    fun updateTitle(id: Long, title: String)

    @Query("UPDATE diarys SET diary_message = :message WHERE id = :id")
    fun updateMessage(id: Long, message: String)

    @Query("UPDATE diarys SET diary_date = :date WHERE id = :id")
    fun updateDate(id: Long, date: Date)

    @Query("UPDATE diarys SET diary_shared = :isShared WHERE id = :id")
    fun updateIsShared(id: Long, isShared: Boolean)

    @Query("UPDATE diarys SET diary_location = :location WHERE id = :id")
    fun updateLocation(id: Long, location: SeoulLocation)

    @Query("UPDATE diarys SET diary_photo_urls = :photoURLs WHERE id = :id")
    fun updatePhotoUrl(id: Long, photoURLs: List<String>)

    @Query("UPDATE diarys SET diary_thumbnail= :thumbNail WHERE id = :id")
    fun updateThumbnail(id: Long, thumbNail: Int)

    // 탐색
    @Query("SELECT * FROM diarys")
    fun getAll(): List<Diary>

    @Query("SELECT * FROM diarys where id = :id")
    fun getDiary(id: Long): Diary

}