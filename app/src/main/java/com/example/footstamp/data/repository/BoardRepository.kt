package com.example.footstamp.data.repository

import com.example.footstamp.data.data_source.BoardService
import com.example.footstamp.ui.base.BaseRepository
import javax.inject.Inject

class BoardRepository @Inject constructor(
    private val boardService: BoardService
) : BaseRepository() {
}