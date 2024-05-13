package com.example.footstamp.ui.view.map

import android.graphics.Bitmap
import com.example.footstamp.data.model.Diary
import com.example.footstamp.data.repository.DiaryRepository
import com.example.footstamp.data.util.SeoulLocation
import com.example.footstamp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val repository: DiaryRepository
) : BaseViewModel() {

    private val _screenMapState = MutableStateFlow<SeoulLocation?>(null)
    val screenMapState = _screenMapState.asStateFlow()

    private val _diaries = MutableStateFlow<List<Diary>>(emptyList())
    val diaries = _diaries.asStateFlow()

    private val _readingDiary = MutableStateFlow<Diary?>(null)
    val readingDiary = _readingDiary.asStateFlow()

    private val _openingImage = MutableStateFlow<Bitmap?>(null)
    val openingImage = _openingImage.asStateFlow()

    init {
        getDiariesFromDB()
        getDiaries()
    }

    private fun getDiaries() {
        coroutineLoading {
            repository.getDiaries()
        }
    }

    private fun getDiariesFromDB() {
        coroutineLoading {
            repository.getAllDao().let { diaryList ->
                if (diaryList.isNotEmpty()) _diaries.value = diaryList
            }
        }
    }

    fun updateMapState(location: SeoulLocation) {
        _screenMapState.value = location

    }

    fun dismissMapState() {
        _screenMapState.value = null
    }

    fun showReadScreen(diary: Diary) {
        _readingDiary.value = diary
    }

    fun hideReadScreen() {
        _readingDiary.value = null
    }

    fun getLocationDiariesCount(location: SeoulLocation): Int {
        return _diaries.value.count { it.location == location }
    }

    fun openImageDetail(image: Bitmap) {
        _openingImage.value = image
    }

    fun closeImage() {
        _openingImage.value = null
    }
}