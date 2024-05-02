package com.example.footstamp.ui.screen.board

import android.graphics.Bitmap
import androidx.lifecycle.viewModelScope
import com.example.footstamp.data.data_source.BoardService
import com.example.footstamp.data.model.Diary
import com.example.footstamp.data.repository.BoardRepository
import com.example.footstamp.data.repository.BoardSortType
import com.example.footstamp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoardViewModel @Inject constructor(
    private val repository: BoardRepository, private val boardService: BoardService
) : BaseViewModel() {

    private val _diaries = MutableStateFlow<List<Diary>>(emptyList())
    val diaries = _diaries.asStateFlow()

    private val _boardState = MutableStateFlow<BoardSortType>(BoardSortType.RECENT)
    val boardState = _boardState.asStateFlow()

    private val _readingDiary = MutableStateFlow<Diary?>(null)
    val readingDiary = _readingDiary.asStateFlow()

    private val _openingImage = MutableStateFlow<Bitmap?>(null)
    val openingImage = _openingImage.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getBoardDiaryList(BoardSortType.RECENT)?.let { diaryList ->
                _diaries.value = diaryList
            }
        }
    }

    fun changeBoardState() {
        viewModelScope.launch(Dispatchers.IO) {
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
    }

    fun hideReadScreen() {
        _readingDiary.value = null
    }

    fun openImageDetail(image: Bitmap) {
        _openingImage.value = image
    }

    fun closeImage() {
        _openingImage.value = null
    }
}