package com.example.footstamp.ui.screen.board

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.footstamp.data.model.Diary
import com.example.footstamp.data.repository.DiaryRepository
import com.example.footstamp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoardViewModel @Inject constructor(
    private val repository: DiaryRepository
) : BaseViewModel() {

    private val _diaries = MutableStateFlow<List<Diary>>(emptyList())
    val diaries = _diaries.asStateFlow()

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
}