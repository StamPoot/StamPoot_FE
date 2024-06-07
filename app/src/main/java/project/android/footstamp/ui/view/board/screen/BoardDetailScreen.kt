package project.android.footstamp.ui.view.board.screen

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import project.android.footstamp.R
import project.android.footstamp.data.model.Comment
import project.android.footstamp.data.model.Diary
import project.android.footstamp.data.model.Profile
import project.android.footstamp.data.util.Formatter
import project.android.footstamp.ui.base.BaseScreen
import project.android.footstamp.ui.components.BodyLargeText
import project.android.footstamp.ui.components.BodyText
import project.android.footstamp.ui.components.CommonButton
import project.android.footstamp.ui.components.HalfDialog
import project.android.footstamp.ui.components.ImageDialog
import project.android.footstamp.ui.components.ImagesLayout
import project.android.footstamp.ui.components.LabelText
import project.android.footstamp.ui.components.SpaceMaker
import project.android.footstamp.ui.components.TextInput
import project.android.footstamp.ui.components.TitleLargeText
import project.android.footstamp.ui.components.TitleText
import project.android.footstamp.ui.theme.BackColor
import project.android.footstamp.ui.theme.BlackColor
import project.android.footstamp.ui.theme.MainColor
import project.android.footstamp.ui.theme.SubColor
import project.android.footstamp.ui.theme.TransparentColor
import project.android.footstamp.ui.view.board.BoardViewModel

@Composable
fun BoardDetailScreen(boardViewModel: BoardViewModel = hiltViewModel()) {
    val readingDiary by boardViewModel.readingDiary.collectAsState()
    val openingImage by boardViewModel.openingImage.collectAsState()
    val commentList by boardViewModel.commentList.collectAsState()
    val writerState by boardViewModel.writerState.collectAsState()
    val reportState by boardViewModel.reportState.collectAsState()

    BaseScreen { paddingValue, screenWidth, screenHeight ->
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = screenWidth / 12),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            writerState?.let {
                WriterLayout(writer = it, screenWidth = screenWidth, screenHeight = screenHeight)
            }
            BoardDateAndLocationLayout(screenHeight = screenHeight, readingDiary = readingDiary!!)
            BoardDetailReadLayout(readingDiary = readingDiary!!,
                screenWidth = screenWidth,
                screenHeight = screenHeight,
                onClick = { boardViewModel.openImageDetail(it) })
            BoardShareLayout(
                diary = readingDiary!!,
                onTapLike = { boardViewModel.likeDiary() },
                commentList = commentList,
                screenWidth = screenWidth,
                onWriteComment = { comment -> boardViewModel.writeComment(comment) },
                onDeleteComment = { id -> boardViewModel.deleteCommentAlert(id) }
            )
        }
        // 사진 크게보기
        openingImage?.let { ImageDialog(image = it, onClick = { boardViewModel.closeImage() }) }
        reportState?.let {
            BoardReportDialog(
                onDismiss = { boardViewModel.hideReportDialog() },
                onConfirm = { boardViewModel.reportDiary(it) }
            )
        }
    }
}

@Composable
fun WriterLayout(writer: Profile, screenWidth: Dp, screenHeight: Dp) {
    Card(
        colors = CardColors(
            BackColor, TransparentColor, TransparentColor, TransparentColor
        )
    ) {
        Row(
            modifier = Modifier.size(screenWidth * 9 / 10, screenWidth / 8),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SpaceMaker(width = 10.dp)
            Card(shape = CircleShape) {
                AsyncImage(
                    model = writer.image.let {
                        if (it == null) R.drawable.icon_circle_small
                        else Formatter.convertStringToBitmap(it)
                    },
                    modifier = Modifier.size(screenWidth / 10, screenWidth / 10),
                    contentDescription = null
                )
            }
            SpaceMaker(width = 10.dp)
            BodyText(
                text = writer.nickname, color = MainColor, textAlign = TextAlign.Start
            )
        }
    }
}


@Composable
fun BoardDateAndLocationLayout(screenHeight: Dp, readingDiary: Diary) {
    SpaceMaker(height = screenHeight / 40)
    Row(
        horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.CalendarMonth,
                contentDescription = null,
                tint = MainColor
            )
            TitleText(
                text = Formatter.dateTimeToString(readingDiary.date),
                color = MainColor,
                textAlign = TextAlign.Start
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.PinDrop, contentDescription = null, tint = MainColor)
            TitleText(
                text = readingDiary.location.location,
                color = MainColor,
                textAlign = TextAlign.Start
            )
        }
    }
}

@Composable
fun BoardDetailReadLayout(
    readingDiary: Diary, screenWidth: Dp, screenHeight: Dp, onClick: (Bitmap) -> Unit
) {
    SpaceMaker(height = screenHeight / 20)
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
        TitleLargeText(text = readingDiary.title, color = BlackColor)
    }
    SpaceMaker(height = screenHeight / 40)
    ImagesLayout(
        selectedImages = readingDiary.photoBitmapStrings.map { Formatter.convertStringToBitmap(it) },
        screenWidth = screenWidth,
        screenHeight = screenHeight,
        onClickPhoto = onClick
    )
    SpaceMaker(screenHeight / 40)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        BodyText(
            text = readingDiary.message, color = BlackColor, minLines = 3
        )
    }
    SpaceMaker(height = 5.dp)
}

@Composable
fun BoardShareLayout(
    diary: Diary,
    commentList: List<Comment>,
    screenWidth: Dp,
    onTapLike: () -> Unit,
    onWriteComment: (String) -> Unit,
    onDeleteComment: (id: Long) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        BoardCommentLayout(
            diary = diary,
            commentList = commentList,
            screenWidth = screenWidth,
            onTapLike = onTapLike,
            onDeleteComment = onDeleteComment
        )
        BoardCommentWriteLayout(onWriteComment)
    }
}

@Composable
fun BoardCommentLayout(
    diary: Diary,
    screenWidth: Dp,
    commentList: List<Comment>,
    onTapLike: () -> Unit,
    onDeleteComment: (id: Long) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TitleLargeText(text = stringResource(R.string.board_comment), color = MainColor)
            SpaceMaker(width = 10.dp)
            Icon(
                imageVector = Icons.Default.Star,
                modifier = Modifier.clickable { onTapLike() },
                contentDescription = null,
                tint = MainColor
            )
            SpaceMaker(width = 2.dp)
            LabelText(text = diary.likes.toString(), color = MainColor)
        }
        SpaceMaker(height = 10.dp)
        HorizontalDivider(thickness = 1.dp, color = SubColor)
        SpaceMaker(height = 10.dp)
        Column {
            commentList.map {
                BoardComment(
                    comment = it,
                    screenWidth = screenWidth,
                    onDeleteComment = onDeleteComment
                )
            }
        }
        SpaceMaker(height = 10.dp)
    }
}

@Composable
fun BoardCommentWriteLayout(onWriteComment: (String) -> Unit) {
    val emptyString = stringResource(id = R.string.empty_string)
    val commentText = remember { mutableStateOf(emptyString) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(BackColor),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextInput(
            onValueChange = {},
            hint = stringResource(id = R.string.board_comment_input),
            modifier = Modifier.weight(0.9f),
            textState = commentText
        )
        Icon(
            painter = painterResource(R.drawable.icon_pen),
            contentDescription = null,
            modifier = Modifier
                .weight(0.1f)
                .fillMaxHeight()
                .clickable {
                    onWriteComment(commentText.value)
                    commentText.value = emptyString
                },
            tint = MainColor
        )
    }
    SpaceMaker(height = 50.dp)
}

@Composable
fun BoardComment(comment: Comment, screenWidth: Dp, onDeleteComment: (id: Long) -> Unit) {
    SpaceMaker(height = 5.dp)
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardColors(BackColor, BackColor, BackColor, BackColor)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Card(shape = CircleShape, modifier = Modifier.padding(horizontal = 5.dp)) {
                AsyncImage(
                    model = comment.profileImage ?: R.drawable.icon_circle_small,
                    modifier = Modifier.size(screenWidth / 10, screenWidth / 10),
                    contentDescription = null
                )
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .weight(0.7f)
            ) {
                SpaceMaker(height = 5.dp)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    BodyText(
                        text = comment.nickname,
                        color = MainColor,
                        modifier = Modifier.padding(horizontal = 5.dp)
                    )
                    if (comment.isMine) AsyncImage(
                        model = R.drawable.icon_my,
                        contentDescription = null,
                        modifier = Modifier.fillMaxHeight()
                    )
                }
                SpaceMaker(height = 5.dp)
                BodyLargeText(
                    text = comment.content,
                    color = BlackColor,
                    modifier = Modifier.padding(horizontal = 5.dp)
                )
                SpaceMaker(height = 5.dp)
            }
            Icon(imageVector = Icons.Default.Close,
                contentDescription = null,
                tint = SubColor,
                modifier = Modifier
                    .weight(0.1f)
                    .clickable { onDeleteComment(comment.id) })
        }
        LabelText(
            text = comment.date, color = SubColor, modifier = Modifier.padding(horizontal = 5.dp)
        )
        SpaceMaker(height = 5.dp)
    }
    SpaceMaker(height = 5.dp)
}

@Composable
fun BoardReportDialog(onDismiss: () -> Unit, onConfirm: (reason: String) -> Unit) {
    HalfDialog(onChangeState = { onDismiss() }) {
        val textInput = remember { mutableStateOf("") }
        TitleLargeText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            text = stringResource(id = R.string.board_alert_comment_report),
            color = MainColor,
            textAlign = TextAlign.Center
        )
        TitleText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            text = stringResource(id = R.string.board_alert_comment_report_message),
            color = BackColor,
            textAlign = TextAlign.Center
        )
        TextInput(
            modifier = Modifier.fillMaxWidth(0.9f),
            textState = textInput
        ) {}
        Row(
            modifier = Modifier.fillMaxWidth(0.9f),
            horizontalArrangement = Arrangement.Center
        ) {
            CommonButton(onClick = { onConfirm(textInput.value) })
            SpaceMaker(width = 10.dp)
            CommonButton(onClick = { onDismiss() })
        }
    }
}