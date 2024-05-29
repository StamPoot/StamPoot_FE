package project.android.footstamp.ui.view.board

import android.graphics.Bitmap
import project.android.footstamp.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import project.android.footstamp.data.model.Alert
import project.android.footstamp.data.model.ButtonCount
import project.android.footstamp.data.model.Comment
import project.android.footstamp.data.model.Diary
import project.android.footstamp.data.model.Profile
import project.android.footstamp.data.repository.BoardSortType
import project.android.footstamp.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class BoardViewModel @Inject constructor(
    private val fetchBoardDiariesUseCase: FetchBoardDiariesUseCase,
    private val fetchDiaryDetailUseCase: FetchDiaryDetailUseCase,
    private val fetchAddReplyUseCase: FetchAddReplyUseCase,
    private val likeUseCase: LikeUseCase,
    private val deleteReplyUseCase: DeleteReplyUseCase
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
            fetchBoardDiariesUseCase(BoardSortType.RECENT)?.let { diaryList ->
                _diaries.value = diaryList
            }
        }
    }

    private fun getDiaryDetail() {
        coroutineLoading {
            fetchDiaryDetailUseCase(_readingDiary.value!!.id.toString()).let { triple ->
                val diary = triple.first
                val writer = triple.second
                val comments = triple.third

                _readingDiary.value = diary
                _writerState.value = writer
                _commentList.value = comments
            }
        }
    }

    fun changeBoardState() {
        coroutineLoading {
            _boardState.value = when (_boardState.value) {
                BoardSortType.RECENT -> {
                    fetchBoardDiariesUseCase(BoardSortType.LIKE)
                    BoardSortType.LIKE
                }

                BoardSortType.LIKE -> {
                    fetchBoardDiariesUseCase(BoardSortType.RECENT)
                    BoardSortType.RECENT
                }
            }
        }
    }

    fun likeDiary() {
        coroutineLoading {
            likeUseCase(id = _readingDiary.value!!.id.toString())?.let { likeCount ->
                getDiaryDetail()
            }
        }
    }

    fun writeComment(comment: String) {
        coroutineLoading {
            fetchAddReplyUseCase(
                id = _readingDiary.value!!.id.toString(),
                content = comment
            ).let { isSuccessful ->
                if (isSuccessful) {
                    getDiaryDetail().let {
                        val alert = Alert(
                            title = R.string.board_alert_commented,
                            message = R.string.empty_string,
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

    fun deleteCommentAlert(id: Long) {
        val alert = Alert(
            title = R.string.board_alert_comment_delete,
            message = R.string.board_alert_comment_delete_message,
            ButtonCount.TWO,
            onPressYes = {
                deleteComment(id)
                hideAlert()
            },
            onPressNo = { hideAlert() }
        )

        showAlert(alert)
    }

    private fun deleteComment(id: Long) {
        coroutineLoading {
            deleteReplyUseCase(id.toString()).let { isSuccessful ->
                if (isSuccessful) {
                    getDiaryDetail().let {
                        val alert = Alert(
                            title = R.string.board_alert_comment_deleted,
                            message = R.string.empty_string,
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

    fun openImageDetail(image: Bitmap) {
        _openingImage.value = image
    }

    fun closeImage() {
        _openingImage.value = null
    }
}