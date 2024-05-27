package com.example.footstamp.ui.view.gallery

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.viewModelScope
import com.example.footstamp.R
import com.example.footstamp.data.model.Alert
import com.example.footstamp.data.model.ButtonCount
import com.example.footstamp.data.model.Diary
import com.example.footstamp.data.util.SeoulLocation
import com.example.footstamp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val fetchDiariesUseCase: FetchDiariesUseCase,
    private val writeDiaryUseCase: WriteDiaryUseCase,
    private val readDiaryUseCase: ReadDiaryUseCase,
    private val deleteDiaryUseCase: DeleteDiaryUseCase,
    private val updateDiaryUseCase: UpdateDiaryUseCase,
    private val shareDiaryUseCase: ShareDiaryUseCase,
    private val getAllDiaryDaoUseCase: GetAllDiaryDaoUseCase
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
        getDiariesFromDB()
        updateDiariesState()
    }

    private fun updateDiariesState() {
        coroutineLoading {
            fetchDiariesUseCase().let {
                if (it != null) _diaries.value = it
            }
        }
    }

    private fun getDiariesFromDB() {
        viewModelScope.launch(Dispatchers.IO) {
            getAllDiaryDaoUseCase().let { diaryList ->
                if (diaryList.isNotEmpty()) _diaries.value = diaryList
            }
        }
    }

    fun writeDiary(context: Context) {
        if (_editingDiary.value.checkDiary() != null) return
        coroutineLoading {
            writeDiaryUseCase(_editingDiary.value, context).let { isSuccessful ->
                if (isSuccessful) {
                    val alert = Alert(title = R.string.gallery_alert_write_written,
                        message = R.string.empty_string,
                        buttonCount = ButtonCount.ONE,
                        onPressYes = { hideAlert() })
                    initializeViewState()
                    showAlert(alert)
                } else {
                    showError()
                }
            }
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

    fun updateDiaryAlert(context: Context) {
        val alert = Alert(title = R.string.gallery_alert_ask_edit,
            message = R.string.empty_string,
            buttonCount = ButtonCount.TWO,
            onPressYes = {
                updateDiary(context)
                hideAlert()
            },
            onPressNo = { hideAlert() })
        showAlert(alert)
    }

    private fun updateDiary(context: Context) {
        coroutineLoading {
            updateDiaryUseCase(_editingDiary.value, context).let { isSuccessful ->
                if (isSuccessful) {
                    initializeViewState()
                } else {
                    showError()
                }
            }
        }
    }

    fun deleteDiaryAlert() {
        val alert = Alert(title = R.string.gallery_alert_ask_delete,
            message = R.string.gallery_alert_content_delete,
            buttonCount = ButtonCount.TWO,
            onPressYes = {
                deleteDiary()
                hideAlert()
            },
            onPressNo = { hideAlert() })
        showAlert(alert)
    }

    private fun deleteDiary() {
        coroutineLoading {
            deleteDiaryUseCase(_readingDiary.value.id.toString()).let { isSuccessful ->
                if (isSuccessful) {
                    initializeViewState()
                } else {
                    showError()
                }
            }
        }
    }

    fun shareTransDiaryAlert() {
        val alert = Alert(title = if (_readingDiary.value.isShared) {
            R.string.gallery_alert_ask_not_share
        } else {
            R.string.gallery_alert_ask_share
        }, message = if (_readingDiary.value.isShared) {
            R.string.gallery_alert_ask_not_share_content
        } else {
            R.string.gallery_alert_ask_share_content
        }, buttonCount = ButtonCount.TWO, onPressYes = {
            shareTransDiary()
            hideAlert()
        }, onPressNo = { hideAlert() })

        showAlert(alert)
    }

    private fun shareTransDiary() {
        coroutineLoading {
            shareDiaryUseCase(_readingDiary.value.id.toString()).let { isSuccessful ->
                if (isSuccessful) {
                    fetchDiariesUseCase().let {
                        if (it != null) _diaries.value = it
                    }
                    _diaries.value.find { it.id == _readingDiary.value.id }?.let {
                        _readingDiary.value = it
                        val alert = Alert(title = if (_readingDiary.value.isShared) {
                            R.string.gallery_alert_shared
                        } else {
                            R.string.gallery_alert_not_shared
                        },
                            message = R.string.empty_string,
                            buttonCount = ButtonCount.ONE,
                            onPressYes = { hideAlert() })

                        showAlert(alert)
                    }
                } else {
                    showError()
                }
            }
        }
    }

    fun initializeViewState() {
        _viewState.value = GalleryScreenState.NULL
        _dateOrLocation.value = DateAndLocation.NULL
        _openingImage.value = null
        _readingDiary.value = Diary()
        _editingDiary.value = Diary()
        updateDiariesState()
    }

    fun openImageDetail(image: Bitmap) {
        _openingImage.value = image
    }

    fun closeImage() {
        _openingImage.value = null
    }


    fun showWriteScreen() {
        _viewState.value = GalleryScreenState.WRITE
    }

    fun showReadScreen(diary: Diary) {
        _readingDiary.value = diary
        _viewState.value = GalleryScreenState.READ
    }

    fun showEditScreen() {
        if (_readingDiary.value.isShared) {
            val alert = Alert(title = R.string.gallery_alert_cant_edit,
                message = R.string.gallery_alert_cant_edit_content,
                buttonCount = ButtonCount.ONE,
                onPressYes = { hideAlert() })

            showAlert(alert)
        } else {
            _editingDiary.value = _readingDiary.value
            _viewState.value = GalleryScreenState.EDIT
        }
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