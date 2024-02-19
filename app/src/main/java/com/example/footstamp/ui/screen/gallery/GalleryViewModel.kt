package com.example.footstamp.ui.screen.gallery

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.footstamp.data.model.Diary
import com.example.footstamp.data.repository.DiaryRepository
import com.example.footstamp.data.util.SeoulLocation
import com.example.footstamp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val repository: DiaryRepository
) : BaseViewModel() {

    private val _diaries = MutableStateFlow<List<Diary>>(emptyList())
    val diaries = _diaries.asStateFlow()

    private val _writingDiary = MutableStateFlow(Diary())
    val writingDiary = _writingDiary.asStateFlow()

    private val _isShowWriteDialog = MutableStateFlow(false)
    val isShowWriteDialog = _isShowWriteDialog.asStateFlow()

    private val _isShowHalfDialog = MutableStateFlow(false)
    val isShowHalfDialog = _isShowHalfDialog.asStateFlow()

    private val tempDiaries = listOf(
        Diary(
            title = "",
            date = LocalDateTime.now(),
            location = SeoulLocation.CENTRAL,
            message = "",
            photoURLs = listOf(),
            thumbnail = 0,
            uid = ""
        ),
        Diary(
            title = "",
            date = LocalDateTime.now(),
            location = SeoulLocation.CENTRAL,
            message = "",
            photoURLs = listOf(),
            thumbnail = 0,
            uid = ""
        )
    )

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
            repository.getAll().distinctUntilChanged().collect { diaryList ->
                if (diaryList.isEmpty()) {
                    Log.d("TAG", "EMPTY")
                } else {
                    _diaries.value = diaryList
                }
            }
        }
    }

    fun addDiary(diary: Diary) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.insertDiary(diary)
            }
        }
    }

    fun addDiaries(diaries: List<Diary>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.insertDiaries(diaries)
            }
        }
    }

    fun removeDiary(id: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.deleteDiary(id)
            }
        }
    }

    fun updateWriteDiary(
        title: String = writingDiary.value.title,
        date: LocalDateTime = writingDiary.value.date,
        message: String = writingDiary.value.message,
        isShared: Boolean = writingDiary.value.isShared,
        location: SeoulLocation = writingDiary.value.location,
        photoURLs: List<String> = writingDiary.value.photoURLs,
        thumbnail: Int = writingDiary.value.thumbnail,
        uid: String = writingDiary.value.uid
    ) {
        _writingDiary.value =
            Diary(
                title = title,
                date = date,
                message = message,
                isShared = isShared,
                location = location,
                photoURLs = photoURLs,
                thumbnail = thumbnail,
                uid = uid
            )

    }

    fun getAllDiaries(): List<Diary> {
        return diaries.value
    }

    fun showWriteScreen() {
        _isShowWriteDialog.value = true
    }

    fun hideWriteScreen() {
        _isShowWriteDialog.value = false
    }

    fun showHalfDialog() {
        _isShowHalfDialog.value = true
    }

    fun hideHalfDialog() {
        _isShowHalfDialog.value = false
    }
}