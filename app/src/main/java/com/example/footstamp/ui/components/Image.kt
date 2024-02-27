package com.example.footstamp.ui.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.footstamp.R
import com.example.footstamp.ui.theme.BackColor
import com.example.footstamp.ui.theme.MainColor
import com.example.footstamp.ui.theme.SubColor
import kotlin.math.roundToInt

@Composable
fun ImagesLayout(selectedImages: List<Uri>, onClick: (image: Uri) -> Unit = {}) {
    val scrollState = rememberScrollState()
    val itemWeight = LocalConfiguration.current.screenWidthDp.dp
    val itemHeight = LocalConfiguration.current.screenHeightDp.dp

    if (selectedImages.isEmpty()) PhotoItem(
        item = R.drawable.icon_circle_big,
        itemWeight = itemWeight,
        itemHeight = itemHeight
    ) else
        Row(
            modifier = Modifier.horizontalScroll(scrollState)
        ) {
            selectedImages.forEach { item ->
                PhotoItem(
                    item = item,
                    itemWeight = itemWeight,
                    itemHeight = itemHeight,
                    onClick = onClick
                )
            }
        }
}

@Composable
fun PhotoItem(item: Uri, itemWeight: Dp, itemHeight: Dp, onClick: (image: Uri) -> Unit = {}) {
    Box(
        modifier = Modifier
            .height(itemHeight / 3)
            .width(itemWeight * 0.8f)
            .background(BackColor)
    ) {
        AsyncImage(
            model = item,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )
        TransparentButton(onClick = { onClick(item) })
    }
}

@Composable
fun PhotoItem(item: Int, itemWeight: Dp, itemHeight: Dp, onClick: (image: Int) -> Unit = {}) {
    Box(
        modifier = Modifier
            .height(itemHeight / 3)
            .width(itemWeight * 0.8f)
            .background(MainColor)
    ) {
        AsyncImage(
            model = item,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )
        TransparentButton(onClick = { onClick(item) })
    }
}

@Composable
fun PhotoSelector(maxSelectionCount: Int = 5, onClick: (image: Uri) -> Unit) {
    var selectedImages by remember {
        mutableStateOf<List<Uri>>(emptyList())
    }
    val buttonText = "사진 선택"
    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(
            maxItems = maxSelectionCount
        ),
        onResult = { uris -> selectedImages = uris }
    )

    fun launchPhotoPicker() {
        multiplePhotoPickerLauncher.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ImagesLayout(selectedImages = selectedImages, onClick = onClick)
        AddButton(buttonText) { launchPhotoPicker() }
    }
}

@Composable
fun ZoomableImage(image: Uri) {
    val scale = remember { mutableFloatStateOf(1f) }
    val offsetX = remember { mutableFloatStateOf(0f) }
    val offsetY = remember { mutableFloatStateOf(0f) }
    Box(
        modifier = Modifier
            .clip(RectangleShape)
            .fillMaxSize()
            .background(SubColor)
            .offset { IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt()) }
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    offsetX.value += dragAmount.x
                    offsetY.value += dragAmount.y
                }
            }
    ) {
        AsyncImage(
            model = image,
            modifier = Modifier
                .align(Alignment.Center)
                .graphicsLayer(
                    scaleX = maxOf(.5f, minOf(3f, scale.value)),
                    scaleY = maxOf(.5f, minOf(3f, scale.value)),
                )
                .pointerInput(Unit) {
                    detectTransformGestures { centroid, pan, zoom, rotation ->
                        scale.floatValue *= zoom
                    }
                },
            contentDescription = null
        )
    }
}