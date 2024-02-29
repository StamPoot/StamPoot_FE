package com.example.footstamp.ui.screen.gallery

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.footstamp.R
import com.example.footstamp.data.model.Diary
import com.example.footstamp.data.util.Formatter
import com.example.footstamp.data.util.SeoulLocation
import com.example.footstamp.ui.base.BaseScreen
import com.example.footstamp.ui.components.BodyLargeText
import com.example.footstamp.ui.components.FullDialog
import com.example.footstamp.ui.components.ImageDialog
import com.example.footstamp.ui.components.TitleLargeText
import com.example.footstamp.ui.components.TitleText
import com.example.footstamp.ui.components.TopBar
import com.example.footstamp.ui.components.TransparentButton
import com.example.footstamp.ui.theme.MainColor
import com.example.footstamp.ui.theme.SubColor
import java.time.LocalDateTime

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun GalleryScreen(galleryViewModel: GalleryViewModel = hiltViewModel()) {

    val writeOrReadScreenState by galleryViewModel.writeOrRead.collectAsState()
    val sortType by galleryViewModel.sortType.collectAsState()

    BaseScreen(floatingButton = {
        GalleryFloatingButton {
            galleryViewModel.showWriteScreen()
        }
    }) { paddingValue ->
        val currentDiary by galleryViewModel.diaries.collectAsState()

        val diaryList = listOf(
            Diary(
                title = "햄스터의 날",
                date = LocalDateTime.now().minusDays(10),
                message = "햄스터를 키웠던 날이에요",
                isShared = false,
                location = SeoulLocation.EAST,
                photoURLs = listOf(
                    "https://i.namu.wiki/i/QQZ3wT3XWJ9FveqmsNKBXQz3O5S-AX3HxTs4ncjzTbUwmnJJlKQyMrQRlOlitttbylEWLViDSzIeRepZTfFj7dh_ZBJeh0Z0Rcr4G6EaeFDySQwUHoB5sPkp6PqOP9sA83-_4P1UyEwfW1s8TLcTQA.webp",
                    "https://i.namu.wiki/i/QQZ3wT3XWJ9FveqmsNKBXQz3O5S-AX3HxTs4ncjzTbUwmnJJlKQyMrQRlOlitttbylEWLViDSzIeRepZTfFj7dh_ZBJeh0Z0Rcr4G6EaeFDySQwUHoB5sPkp6PqOP9sA83-_4P1UyEwfW1s8TLcTQA.webp",
                    "https://i.namu.wiki/i/QQZ3wT3XWJ9FveqmsNKBXQz3O5S-AX3HxTs4ncjzTbUwmnJJlKQyMrQRlOlitttbylEWLViDSzIeRepZTfFj7dh_ZBJeh0Z0Rcr4G6EaeFDySQwUHoB5sPkp6PqOP9sA83-_4P1UyEwfW1s8TLcTQA.webp"
                ),
                thumbnail = 0,
                uid = "a1234"
            ),
            Diary(
                title = "햄스터의 성장",
                date = LocalDateTime.now().minusDays(3),
                message = "햄스터가 성장한 날이에요",
                isShared = false,
                location = SeoulLocation.CENTRAL,
                photoURLs = listOf("https://i.namu.wiki/i/hLKYGp1WjQtZHr8ToxGT5uArhm_PIofVFdhytyMIAg_86n-Xxu-w0JzOpHYmw7abCxCqAdOP_NkTvTKUYE-Vb0edjhahGLCQ6tx0hsfg3LG3meX8t0_dDSkBGinhdctoZRaFIR2TE1rXHO6BIVu2Xw.webp"),
                thumbnail = 0,
                uid = "a1234"
            ),
            Diary(
                title = "햄스터의 햄스터",
                date = LocalDateTime.now().minusDays(7),
                message = "햄스터가 햄스터한 날이에요",
                isShared = false,
                location = SeoulLocation.WEST,
                photoURLs = listOf("https://i.namu.wiki/i/OkhQuyObzv60hx9Mur4W_hQvvKrgf_mlBFYiSs_WVNjfV7-2LnLKSz_yf99XKQB1FxpzXbzymxqTEgr2JjO7jn6jHGn1U_FEcECV9ZE9Yyx-R65U_59o99aU-ozL2nuRsnJ8x6OyzoeaL6E473vd0w.webp"),
                thumbnail = 0,
                uid = "a1234"
            ),
            Diary(
                title = "햄스터의 분노",
                date = LocalDateTime.now().minusDays(14),
                message = "햄스터가 햄스터한 날이에요",
                isShared = false,
                location = SeoulLocation.NORTH,
                photoURLs = listOf("https://i.namu.wiki/i/OkhQuyObzv60hx9Mur4W_hQvvKrgf_mlBFYiSs_WVNjfV7-2LnLKSz_yf99XKQB1FxpzXbzymxqTEgr2JjO7jn6jHGn1U_FEcECV9ZE9Yyx-R65U_59o99aU-ozL2nuRsnJ8x6OyzoeaL6E473vd0w.webp"),
                thumbnail = 0,
                uid = "a1234"
            ),
            Diary(
                title = "햄스터의 진노",
                date = LocalDateTime.now().minusDays(21),
                message = "햄스터가 햄스터한 날이에요",
                isShared = false,
                location = SeoulLocation.SOUTH,
                photoURLs = listOf("https://i.namu.wiki/i/OkhQuyObzv60hx9Mur4W_hQvvKrgf_mlBFYiSs_WVNjfV7-2LnLKSz_yf99XKQB1FxpzXbzymxqTEgr2JjO7jn6jHGn1U_FEcECV9ZE9Yyx-R65U_59o99aU-ozL2nuRsnJ8x6OyzoeaL6E473vd0w.webp"),
                thumbnail = 0,
                uid = "a1234"
            ),
        )

        Column {
            TopBar(text = stringResource(R.string.screen_gallery), backgroundColor = Color.White)
            GallerySortLayout(
                sortType = sortType,
                onChangeSortState = { galleryViewModel.changeSortSwitch() })

            GalleryGridLayout(diaries = diaryList,
                paddingValues = paddingValue,
                sortType = sortType,
                onClick = { galleryViewModel.showReadScreen(it) })
        }

        GalleryReadOrWriteScreen(
            writeOrReadScreenState = writeOrReadScreenState,
            onChangeState = { galleryViewModel.hideWriteOrReadScreen() }
        )
    }
}

@Composable
fun GallerySortLayout(
    sortType: GalleryViewModel.SortByDateOrLocation,
    onChangeSortState: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BodyLargeText(text = sortType.text, color = MainColor)
        Switch(
            modifier = Modifier.padding(vertical = 10.dp),
            checked = sortType == GalleryViewModel.SortByDateOrLocation.DATE,
            onCheckedChange = { onChangeSortState() })
    }
}

@Composable
fun GalleryGridLayout(
    diaries: List<Diary>,
    paddingValues: PaddingValues,
    sortType: GalleryViewModel.SortByDateOrLocation,
    onClick: (Diary) -> Unit
) {
    val scrollState = rememberScrollState()
    val itemHeight = LocalConfiguration.current.screenHeightDp.dp / 4

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
            GalleryItemView(diary = diary, itemHeight = itemHeight, onClick = onClick)
        }
    }
}

@Composable
fun GalleryItemView(diary: Diary, itemHeight: Dp, onClick: (Diary) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(itemHeight)
            .background(SubColor)
    ) {
        AsyncImage(
            model = diary.photoURLs[diary.thumbnail],
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
            TitleText(Formatter.dateToString(diary.date), Color.White)
            TitleLargeText(diary.title, Color.White)
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
    writeOrReadScreenState: GalleryViewModel.WriteAndRead,
    onChangeState: () -> Unit
) {
    when (writeOrReadScreenState) {
        GalleryViewModel.WriteAndRead.WRITE -> {
            FullDialog(
                title = GalleryViewModel.WriteAndRead.WRITE.text,
                screen = { GalleryWriteScreen() },
                onChangeState = onChangeState
            )
        }

        GalleryViewModel.WriteAndRead.READ -> {
            FullDialog(
                title = GalleryViewModel.WriteAndRead.READ.text,
                screen = { GalleryReadScreen() },
                onChangeState = onChangeState
            )
        }

        GalleryViewModel.WriteAndRead.NULL -> {}
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
            tint = Color.White
        )
    }
}