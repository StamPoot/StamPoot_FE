package com.example.footstamp.ui.view.board.screen

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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.footstamp.R
import com.example.footstamp.data.model.Comment
import com.example.footstamp.data.model.Diary
import com.example.footstamp.data.model.Profile
import com.example.footstamp.data.util.Formatter
import com.example.footstamp.ui.base.BaseScreen
import com.example.footstamp.ui.components.BodyLargeText
import com.example.footstamp.ui.components.BodyText
import com.example.footstamp.ui.components.ImageDialog
import com.example.footstamp.ui.components.ImagesLayout
import com.example.footstamp.ui.components.LabelText
import com.example.footstamp.ui.components.SpaceMaker
import com.example.footstamp.ui.components.TextInput
import com.example.footstamp.ui.components.TitleLargeText
import com.example.footstamp.ui.components.TitleText
import com.example.footstamp.ui.theme.BackColor
import com.example.footstamp.ui.theme.BlackColor
import com.example.footstamp.ui.theme.MainColor
import com.example.footstamp.ui.theme.SubColor
import com.example.footstamp.ui.theme.TransparentColor
import com.example.footstamp.ui.view.board.BoardViewModel

@Composable
fun BoardDetailScreen(boardViewModel: BoardViewModel = hiltViewModel()) {
    val readingDiary by boardViewModel.readingDiary.collectAsState()
    val openingImage by boardViewModel.openingImage.collectAsState()
    val commentList by boardViewModel.commentList.collectAsState()
    val writerState by boardViewModel.writerState.collectAsState()

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
            BoardShareLayout(diary = readingDiary!!,
                onTapLike = { boardViewModel.likeDiary() },
                commentList = commentList,
                screenWidth = screenWidth,
                onWriteComment = { comment -> boardViewModel.writeComment(comment) })
        }
        // 사진 크게보기
        openingImage?.let { ImageDialog(image = it, onClick = { boardViewModel.closeImage() }) }
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
                text = Formatter.dateToUserString(readingDiary.date),
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
    onWriteComment: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        BoardCommentLayout(
            diary = diary,
            commentList = commentList,
            screenWidth = screenWidth,
            onTapLike = onTapLike
        )
        BoardCommentWriteLayout(onWriteComment)
    }
}

@Composable
fun BoardCommentLayout(
    diary: Diary, screenWidth: Dp, commentList: List<Comment>, onTapLike: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TitleLargeText(text = "댓글", color = MainColor)
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
            commentList.map { BoardComment(comment = it, screenWidth = screenWidth) }
        }
        SpaceMaker(height = 10.dp)
    }
}

@Composable
fun BoardCommentWriteLayout(onWriteComment: (String) -> Unit) {
    val commentText = remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(BackColor),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextInput(
            onValueChange = {},
            hint = "댓글을 입력해주세요",
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
                    commentText.value = ""
                },
            tint = MainColor
        )
    }
    SpaceMaker(height = 50.dp)
}

@Composable
fun BoardComment(comment: Comment, screenWidth: Dp) {
    SpaceMaker(height = 5.dp)
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardColors(BackColor, BackColor, BackColor, BackColor)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            SpaceMaker(width = 5.dp)
            Card(shape = CircleShape) {
                AsyncImage(
                    model = comment.profileImage ?: R.drawable.icon_circle_small,
                    modifier = Modifier.size(screenWidth / 10, screenWidth / 10),
                    contentDescription = null
                )
            }
            SpaceMaker(width = 5.dp)
            Column {
                SpaceMaker(height = 5.dp)
                BodyText(
                    text = comment.nickname,
                    color = MainColor,
                    modifier = Modifier.padding(horizontal = 5.dp)
                )
                SpaceMaker(height = 5.dp)
                BodyLargeText(
                    text = comment.content,
                    color = BlackColor,
                    modifier = Modifier.padding(horizontal = 5.dp)
                )
                SpaceMaker(height = 5.dp)
            }
        }
        LabelText(
            text = comment.date,
            color = SubColor,
            modifier = Modifier.padding(horizontal = 5.dp)
        )
        SpaceMaker(height = 5.dp)
    }
    SpaceMaker(height = 5.dp)
}