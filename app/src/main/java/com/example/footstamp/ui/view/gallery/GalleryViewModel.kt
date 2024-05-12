package com.example.footstamp.ui.view.gallery

import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
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

    private val _editingDiary = MutableStateFlow(Diary())
    val editingDiary = _editingDiary.asStateFlow()

    private val _readingDiary = MutableStateFlow(Diary())
    val readingDiary = _readingDiary.asStateFlow()

    private val _openingImage = MutableStateFlow<Bitmap?>(null)
    val openingImage = _openingImage.asStateFlow()

    private val _viewState = MutableStateFlow(GalleryScreenState.NULL)
    val viewState = _viewState.asStateFlow()

    private val _dateOrLocation = MutableStateFlow(DateAndLocation.NULL)
    val dateOrLocation = _dateOrLocation.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllDao().distinctUntilChanged().collect { diaryList ->
                if (diaryList.isNotEmpty()) _diaries.value = diaryList
            }
        }
        updateDiariesState()
    }

    private fun updateDiariesState() {
        viewModelScope.launch(Dispatchers.IO) {
            startLoading()
            repository.getDiaries()
            finishLoading()
        }
    }

    fun addDiary(context: Context) {
        if (_editingDiary.value.checkDiary() != null) return

        viewModelScope.launch(Dispatchers.IO) {
            startLoading()
            repository.writeDiary(_editingDiary.value, context)
            finishLoading()
        }
    }

    fun removeDiary(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            startLoading()
            repository.deleteDiaryDao(id)
            finishLoading()
        }
    }

    fun updateWriteDiary(
        title: String = editingDiary.value.title,
        date: LocalDateTime = editingDiary.value.date,
        message: String = editingDiary.value.message,
        isShared: Boolean = editingDiary.value.isShared,
        location: SeoulLocation = editingDiary.value.location,
        photoURLs: List<String> = editingDiary.value.photoBitmapStrings,
        thumbnail: Int = editingDiary.value.thumbnail,
        uid: String = editingDiary.value.uid,
        id: Long = editingDiary.value.id
    ) {
        _editingDiary.value = Diary(
            title = title,
            date = date,
            message = message,
            isShared = isShared,
            location = location,
            photoBitmapStrings = photoURLs,
            thumbnail = thumbnail,
            uid = uid,
        ).apply {
            insertId(id)
        }
    }

    fun updateDiary(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            startLoading()
            repository.updateDiary(_editingDiary.value, context).let { isSuccessful ->
                if (isSuccessful) initializeViewState()
                // TODO : 실패시 대응
            }
            finishLoading()
        }
    }

    fun shareTransDiary() {
        viewModelScope.launch(Dispatchers.IO) {
            startLoading()
            repository.shareDiary(_readingDiary.value.id.toString())
            initializeViewState()
            finishLoading()
        }
    }

    fun transDiaryReadToEdit() {
        _editingDiary.value = _readingDiary.value
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

    fun initializeViewState() {
        _viewState.value = GalleryScreenState.NULL
        _dateOrLocation.value = DateAndLocation.NULL
        _openingImage.value = null
        _readingDiary.value = Diary()
        _editingDiary.value = Diary()
    }

    fun showWriteScreen() {
        _viewState.value = GalleryScreenState.WRITE
    }

    fun showReadScreen(diary: Diary) {
        _readingDiary.value = diary
        _viewState.value = GalleryScreenState.READ
    }

    fun showEditScreen(context: Context) {
        if (_readingDiary.value.isShared) {
            Toast.makeText(context, "공유 중인 일기는 수정할 수 없어요", Toast.LENGTH_SHORT).show()
        }
        _viewState.value = GalleryScreenState.EDIT
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

    enum class GalleryScreenState(val text: String) {
        WRITE("일기 쓰기"), READ("일기 읽기"), EDIT("일기 수정"), NULL("")
    }

    enum class DateAndLocation {
        DATE, LOCATION, NULL
    }
}