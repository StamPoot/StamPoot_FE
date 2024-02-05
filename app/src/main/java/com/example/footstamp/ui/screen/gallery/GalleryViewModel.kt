package com.example.footstamp.ui.screen.gallery

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.example.footstamp.data.model.Diary
import com.example.footstamp.data.repository.DiaryRepository
import com.example.footstamp.data.util.SeoulLocation
import com.example.footstamp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Instant
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val repository: DiaryRepository
) : BaseViewModel() {

    private val _diaries = MutableStateFlow<List<Diary>>(emptyList())
    val diaries = _diaries.asStateFlow()

    private val tempDiaries = listOf(
        Diary(
            title = "",
            date = Date.from(Instant.now()),
            location = SeoulLocation.CENTRAL,
            message = "",
            photoURLs = listOf(),
            thumbnail = 0,
            uid = ""
        ),
        Diary(
            title = "",
            date = Date.from(Instant.now()),
            location = SeoulLocation.CENTRAL,
            message = "",
            photoURLs = listOf(),
            thumbnail = 0,
            uid = ""
        )
    )

    init {
        viewModelScope.launch(Dispatchers.IO) {
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

    fun getAllDiaries(): List<Diary> {
        return diaries.value
    }
}