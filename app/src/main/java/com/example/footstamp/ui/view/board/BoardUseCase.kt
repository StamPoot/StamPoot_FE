package com.example.footstamp.ui.view.board

import com.example.footstamp.data.model.Comment
import com.example.footstamp.data.model.Diary
import com.example.footstamp.data.model.Profile
import com.example.footstamp.data.repository.BoardRepository
import com.example.footstamp.data.repository.BoardSortType
import javax.inject.Inject

class FetchBoardDiariesUseCase @Inject constructor(
    private val boardRepository: BoardRepository
) {
    suspend operator fun invoke(sortType: BoardSortType): List<Diary>? {
        return boardRepository.fetchBoardDiaryList(sortType)
    }
}

class FetchDiaryDetailUseCase @Inject constructor(
    private val boardRepository: BoardRepository
) {
    suspend operator fun invoke(id: String): Triple<Diary, Profile, List<Comment>> {
        return boardRepository.fetchDiaryDetail(id)
    }
}

class FetchAddReplyUseCase @Inject constructor(
    private val boardRepository: BoardRepository
) {
    suspend operator fun invoke(id: String, content: String): Boolean {
        return boardRepository.fetchAddReply(id, content)
    }
}

class LikeUseCase @Inject constructor(
    private val boardRepository: BoardRepository
) {
    suspend operator fun invoke(id: String): Int? {
        return boardRepository.likeDiary(id)
    }
}

class DeleteReplyUseCase @Inject constructor(
    private val boardRepository: BoardRepository
) {
    suspend operator fun invoke(id: String): Boolean {
        return boardRepository.deleteReply(id)
    }
}