package com.example.footstamp.ui.screen.gallery

import android.content.Context
import android.graphics.Bitmap
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

    private val _sortType = MutableStateFlow(SortByDateOrLocation.DATE)
    val sortType = _sortType.asStateFlow()

    private val _writingDiary = MutableStateFlow(Diary())
    val writingDiary = _writingDiary.asStateFlow()

    private val _readingDiary = MutableStateFlow(Diary())
    val readingDiary = _readingDiary.asStateFlow()

    private val _openingImage = MutableStateFlow<Bitmap?>(null)
    val openingImage = _openingImage.asStateFlow()

    private val _writeOrRead = MutableStateFlow(WriteAndRead.NULL)
    val writeOrRead = _writeOrRead.asStateFlow()

    private val _dateOrLocation = MutableStateFlow(DateAndLocation.NULL)
    val dateOrLocation = _dateOrLocation.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllDao().distinctUntilChanged().collect { diaryList ->
                if (diaryList.isNotEmpty()) _diaries.value = diaryList
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            repository.getDiaries()
        }
    }

    fun addDiary(context: Context) {
        if (_writingDiary.value.checkDiary() != null) return

        viewModelScope.launch(Dispatchers.IO) {
            repository.writeDiary(_writingDiary.value, context)
        }
    }

    fun removeDiary(id: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.deleteDiaryDao(id)
            }
        }
    }

    fun updateWriteDiary(
        title: String = writingDiary.value.title,
        date: LocalDateTime = writingDiary.value.date,
        message: String = writingDiary.value.message,
        isShared: Boolean = writingDiary.value.isShared,
        location: SeoulLocation = writingDiary.value.location,
        photoURLs: List<String> = writingDiary.value.photoBitmapStrings,
        thumbnail: Int = writingDiary.value.thumbnail,
        uid: String = writingDiary.value.uid
    ) {
        _writingDiary.value = Diary(
            title = title,
            date = date,
            message = message,
            isShared = isShared,
            location = location,
            photoBitmapStrings = photoURLs,
            thumbnail = thumbnail,
            uid = uid
        )

    }

    fun changeSortSwitch() {
        _sortType.value = when (_sortType.value) {
            SortByDateOrLocation.DATE -> SortByDateOrLocation.LOCATION
            SortByDateOrLocation.LOCATION -> SortByDateOrLocation.DATE
        }
    }

    fun openImageDetail(image: Bitmap) {
        _openingImage.value = image
    }

    fun closeImage() {
        _openingImage.value = null
    }

    fun getAllDiaries(): List<Diary> {
        return diaries.value
    }

    fun hideWriteOrReadScreen() {
        _writeOrRead.value = WriteAndRead.NULL
        _dateOrLocation.value = DateAndLocation.NULL
        _openingImage.value = null
    }

    fun showWriteScreen() {
        _writeOrRead.value = WriteAndRead.WRITE
    }

    fun showReadScreen(diary: Diary) {
        _readingDiary.value = diary
        _writeOrRead.value = WriteAndRead.READ
    }

    fun hideHalfDialog() {
        _dateOrLocation.value = DateAndLocation.NULL
    }

    fun showDateDialog() {
        _dateOrLocation.value = DateAndLocation.DATE
    }

    fun showLocationDialog() {
        _dateOrLocation.value = DateAndLocation.LOCATION
    }

    enum class SortByDateOrLocation(val text: String) {
        DATE("날짜 별 보기"), LOCATION("위치 별 보기")
    }

    enum class WriteAndRead(val text: String) {
        WRITE("일기 쓰기"), READ("일기 읽기"), NULL("")
    }

    enum class DateAndLocation {
        DATE, LOCATION, NULL
    }
}