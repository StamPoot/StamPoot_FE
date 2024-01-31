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
    fun insertDiarys(vararg diarys: Diary)

    @Insert
    fun insertDiary(diary: Diary)

    // 삭제
    @Query("DELETE FROM diarys where id = :id")
    fun deleteDiary(id: Long)

    @Query("DELETE FROM diarys")
    fun deleteAll()

    // 업데이트
    @Query("UPDATE diarys SET diary_name = :name WHERE id = :id")
    fun updateName(id: Long, name: String)

    @Query("UPDATE diarys SET diary_message = :message WHERE id = :id")
    fun updateMessage(id: Long, message: String)

    @Query("UPDATE diarys SET diary_date = :date WHERE id = :id")
    fun updatePrice(id: Long, date: Date)

    @Query("UPDATE diarys SET diary_shared = :isShared WHERE id = :id")
    fun updatePrice(id: Long, isShared: Boolean)

    @Query("UPDATE diarys SET diary_location = :location WHERE id = :id")
    fun updatePrice(id: Long, location: SeoulLocation)

    @Query("UPDATE diarys SET diary_photo_urls = :photoURLs WHERE id = :id")
    fun updatePrice(id: Long, photoURLs: List<String>)

    // 탐색
    @Query("SELECT * FROM diarys")
    fun getAll(): List<Diary>

    @Query("SELECT * FROM diarys where id = :id")
    fun getDiary(id: Long): Diary

}