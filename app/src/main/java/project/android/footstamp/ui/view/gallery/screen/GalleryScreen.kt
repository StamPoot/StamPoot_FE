package project.android.footstamp.ui.view.gallery.screen

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRightAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getString
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import project.android.footstamp.R
import project.android.footstamp.data.model.Diary
import project.android.footstamp.data.util.Formatter
import project.android.footstamp.ui.base.BaseScreen
import project.android.footstamp.ui.components.BodyText
import project.android.footstamp.ui.components.FullDialog
import project.android.footstamp.ui.components.SpaceMaker
import project.android.footstamp.ui.components.TitleLargeText
import project.android.footstamp.ui.components.TitleText
import project.android.footstamp.ui.components.TopBar
import project.android.footstamp.ui.components.TransparentButton
import project.android.footstamp.ui.theme.MainColor
import project.android.footstamp.ui.theme.SubColor
import project.android.footstamp.ui.theme.WhiteColor
import project.android.footstamp.ui.view.gallery.GalleryViewModel
import project.android.footstamp.ui.view.util.AlertScreen
import project.android.footstamp.ui.view.util.LoadingScreen

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun GalleryScreen(
    galleryViewModel: GalleryViewModel = hiltViewModel()
) {
    val isLoading by galleryViewModel.isLoading.collectAsState()
    val alert by galleryViewModel.alertState.collectAsState()

    BaseScreen(floatingButton = {
        GalleryFloatingButton { galleryViewModel.showWriteScreen() }
    }) { paddingValue, screenWidth, screenHeight ->
        val viewState by galleryViewModel.viewState.collectAsState()
        val sortType by galleryViewModel.sortType.collectAsState()
        val diaries by galleryViewModel.diaries.collectAsState()
        val context = LocalContext.current

        Column {
            TopBar(text = stringResource(R.string.screen_gallery), backgroundColor = WhiteColor)

            GalleryGridLayout(diaries = diaries,
                paddingValues = paddingValue,
                sortType = sortType,
                context = context,
                screenHeight = screenHeight,
                onClick = { galleryViewModel.showReadScreen(it) })
        }

        GalleryReadOrWriteScreen(viewState = viewState,
            onChangeState = { galleryViewModel.initializeViewState() },
            onClickWrite = { galleryViewModel.writeDiary(context) },
            onClickMenu = { galleryViewModel.showEditScreen() },
            onClickEdit = { galleryViewModel.updateDiaryAlert(context) })
    }
    isLoading.let { if (it) LoadingScreen() }
    alert?.let { AlertScreen(alert = it) }
}

@Composable
fun GalleryGridLayout(
    diaries: List<Diary>,
    context: Context,
    screenHeight: Dp,
    paddingValues: PaddingValues,
    sortType: GalleryViewModel.SortByDateOrLocation,
    onClick: (Diary) -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
            .padding(paddingValues = paddingValues)
    ) {
        when (sortType) {
            GalleryViewModel.SortByDateOrLocation.DATE -> {
                diaries.sortedByDescending { it.date }
            }

            GalleryViewModel.SortByDateOrLocation.LOCATION -> {
                diaries.sortedBy { it.location }
            }
        }.forEach { diary ->
            GalleryItemView(diary = diary, screenHeight = screenHeight / 4, onClick = onClick)
        }
        if (diaries.isEmpty()) {
            SpaceMaker(height = screenHeight / 3)
            TitleLargeText(
                text = getString(context, R.string.gallery_no_diary),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = SubColor
            )
        }
    }
}

@Composable
fun GalleryItemView(diary: Diary, screenHeight: Dp, onClick: (Diary) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight)
            .background(SubColor)
    ) {
        AsyncImage(
            model = Formatter.convertStringToBitmap(diary.photoBitmapStrings[diary.thumbnail]),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.6f),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleText(Formatter.dateTimeToString(diary.date), WhiteColor)
            SpaceMaker(height = 10.dp)
            TitleLargeText(diary.title, WhiteColor)
            SpaceMaker(height = 10.dp)
            BodyText(diary.location.location, WhiteColor)
        }
        TransparentButton(onClick = { onClick(diary) })
    }
    HorizontalDivider(
        modifier = Modifier
            .height(1.dp)
            .background(SubColor)
    )
}

@Composable
fun GalleryReadOrWriteScreen(
    viewState: GalleryViewModel.GalleryScreenState,
    onChangeState: () -> Unit,
    onClickWrite: () -> Unit,
    onClickMenu: () -> Unit,
    onClickEdit: () -> Unit
) {
    when (viewState) {
        GalleryViewModel.GalleryScreenState.WRITE -> {
            FullDialog(
                title = GalleryViewModel.GalleryScreenState.WRITE.text,
                screen = { GalleryWriteScreen() },
                rightIcon = Icons.AutoMirrored.Filled.ArrowRightAlt,
                onBackIconPressed = onChangeState,
                onClickPressed = onClickWrite
            )
        }

        GalleryViewModel.GalleryScreenState.READ -> {
            FullDialog(
                title = GalleryViewModel.GalleryScreenState.READ.text,
                screen = { GalleryReadScreen() },
                rightIcon = Icons.Default.Edit,
                onBackIconPressed = onChangeState,
                onClickPressed = onClickMenu
            )
        }

        GalleryViewModel.GalleryScreenState.EDIT -> {
            FullDialog(
                title = GalleryViewModel.GalleryScreenState.EDIT.text,
                screen = { GalleryEditScreen() },
                rightIcon = Icons.Default.Check,
                onBackIconPressed = onChangeState,
                onClickPressed = onClickEdit
            )
        }

        GalleryViewModel.GalleryScreenState.NULL -> {}
    }
}

@Composable
fun GalleryFloatingButton(action: () -> Unit) {
    FloatingActionButton(
        modifier = Modifier, containerColor = MainColor, shape = CircleShape, onClick = action
    ) {
        Icon(
            painter = painterResource(R.drawable.icon_pen),
            contentDescription = null,
            tint = WhiteColor
        )
    }
}