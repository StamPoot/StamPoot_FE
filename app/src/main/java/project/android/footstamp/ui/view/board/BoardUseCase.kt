package project.android.footstamp.ui.view.board

import project.android.footstamp.data.model.Comment
import project.android.footstamp.data.model.Diary
import project.android.footstamp.data.model.Profile
import project.android.footstamp.data.repository.BoardRepository
import project.android.footstamp.data.repository.BoardSortType
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