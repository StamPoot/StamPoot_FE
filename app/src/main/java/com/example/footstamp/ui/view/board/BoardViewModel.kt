package com.example.footstamp.ui.view.board

import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.util.Log
import com.example.footstamp.data.model.Alert
import com.example.footstamp.data.model.ButtonCount
import com.example.footstamp.data.model.Comment
import com.example.footstamp.data.model.Diary
import com.example.footstamp.data.model.Profile
import com.example.footstamp.data.repository.BoardRepository
import com.example.footstamp.data.repository.BoardSortType
import com.example.footstamp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class BoardViewModel @Inject constructor(
    private val repository: BoardRepository
) : BaseViewModel() {

    private val _diaries = MutableStateFlow<List<Diary>>(emptyList())
    val diaries = _diaries.asStateFlow()

    private val _boardState = MutableStateFlow<BoardSortType>(BoardSortType.RECENT)
    val boardState = _boardState.asStateFlow()

    private val _readingDiary = MutableStateFlow<Diary?>(null)
    val readingDiary = _readingDiary.asStateFlow()

    private val _writerState = MutableStateFlow<Profile?>(null)
    val writerState = _writerState.asStateFlow()

    private val _commentList = MutableStateFlow<List<Comment>>(emptyList())
    val commentList = _commentList.asStateFlow()

    private val _openingImage = MutableStateFlow<Bitmap?>(null)
    val openingImage = _openingImage.asStateFlow()

    init {
        updateBoardState()
    }

    private fun updateBoardState() {
        coroutineLoading {
            repository.getBoardDiaryList(BoardSortType.RECENT)?.let { diaryList ->
                _diaries.value = diaryList
            }
        }
    }

    fun changeBoardState() {
        coroutineLoading {
            _boardState.value = when (_boardState.value) {
                BoardSortType.RECENT -> {
                    repository.getBoardDiaryList(BoardSortType.LIKE)
                    BoardSortType.LIKE
                }

                BoardSortType.LIKE -> {
                    repository.getBoardDiaryList(BoardSortType.RECENT)
                    BoardSortType.RECENT
                }
            }
        }
    }

    fun showReadScreen(diary: Diary) {
        _readingDiary.value = diary
        getDiaryDetail()
    }

    fun hideReadScreen() {
        _readingDiary.value = null
        _writerState.value = null
        _commentList.value = emptyList()
        updateBoardState()
    }

    fun likeDiary() {
        coroutineLoading {
            repository.likeDiary(id = _readingDiary.value!!.id.toString())?.let { likeCount ->
                getDiaryDetail()
            }
        }
    }

    private fun getDiaryDetail() {
        coroutineLoading {
            repository.getDiaryDetail(_readingDiary.value!!.id.toString()).let { triple ->
                val diary = triple.first
                val writer = triple.second
                val comments = triple.third

                _readingDiary.value = diary
                _writerState.value = writer
                _commentList.value = comments
            }
        }
    }

    fun writeComment(comment: String) {
        coroutineLoading {
            repository.addReply(
                id = _readingDiary.value!!.id.toString(),
                content = comment
            ).let { isSuccessful ->
                if (isSuccessful) {
                    updateBoardState().let {
                        val alert = Alert(
                            title = "댓글이 작성되었습니다",
                            message = "",
                            buttonCount = ButtonCount.ONE,
                            onPressYes = { hideAlert() }
                        )
                        showAlert(alert)
                    }
                } else {
                    showError()
                }
            }
        }
    }

    fun openImageDetail(image: Bitmap) {
        _openingImage.value = image
    }

    fun closeImage() {
        _openingImage.value = null
    }
}