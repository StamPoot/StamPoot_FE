package com.example.footstamp.ui.components

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.footstamp.R
import com.example.footstamp.data.util.Formatter
import com.example.footstamp.ui.theme.MainColor
import com.example.footstamp.ui.theme.SubColor
import kotlin.math.roundToInt

@Composable
fun ImagesLayout(
    selectedImages: List<Bitmap>,
    onClickPhoto: (image: Bitmap) -> Unit = {},
    onClickIndex: (imageIndex: Int) -> Unit = {},
    screenWidth: Dp,
    screenHeight: Dp,
    thumbnailIndex: Int? = null
) {
    val scrollState = rememberScrollState()

    // 이미지가 없을 때
    if (selectedImages.isEmpty()) Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
    ) {
        PhotoItem(
            item = R.drawable.icon_circle_big,
            screenWidth = screenWidth,
            screenHeight = screenHeight
        )
    } else if (selectedImages.size == 1) {
        PhotoItem(item = selectedImages[0],
            screenWidth = screenWidth,
            screenHeight = screenHeight,
            isThumbnail = false,
            onClick = {
                onClickPhoto(selectedImages[0])
                onClickIndex(0)
            })
    }
    // 이미지가 있을 때
    else {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .horizontalScroll(scrollState)
                    .fillMaxWidth(),
            ) {
                selectedImages.forEachIndexed { index, item ->
                    PhotoItem(item = item,
                        screenWidth = screenWidth,
                        screenHeight = screenHeight,
                        isThumbnail = index == thumbnailIndex,
                        onClick = {
                            onClickPhoto(item)
                            onClickIndex(index)
                        })
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
            ) {
                val scope = (scrollState.maxValue + 10) / selectedImages.size
                repeat(selectedImages.size) { index ->
                    Icon(
                        imageVector = Icons.Default.Circle,
                        contentDescription = null,
                        tint = if (scrollState.value / scope == index) MainColor else SubColor,
                        modifier = Modifier
                            .padding(7.dp)
                            .size(10.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileImageLayout(
    image: Bitmap?, screenWidth: Dp, screenHeight: Dp, onClickPhoto: () -> Unit
) {
    Card(shape = CircleShape, onClick = onClickPhoto) {
        if (image != null) {
            AsyncImage(
                model = image,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            AsyncImage(
                model = R.drawable.icon_circle_small,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun PhotoItem(
    item: Bitmap,
    screenWidth: Dp,
    screenHeight: Dp,
    onClick: (image: Bitmap) -> Unit = {},
    isThumbnail: Boolean = false
) {
    Box(
        modifier = Modifier
            .height(screenHeight / 3)
            .width(screenHeight / 3)
            .background(Color.Transparent)
            .padding(15.dp)
            .border(3.dp, if (isThumbnail) MainColor else Color.Transparent),
    ) {
        AsyncImage(
            model = item,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        if (isThumbnail) Box(
            modifier = Modifier
                .background(MainColor)
                .padding(5.dp)
        ) {
            Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = Color.White)
        }
        TransparentButton(onClick = { onClick(item) })
    }
}

@Composable
fun PhotoItem(item: Int, screenWidth: Dp, screenHeight: Dp, onClick: (image: Int) -> Unit = {}) {
    Box(
        modifier = Modifier
            .height(screenHeight / 3)
            .width(screenWidth * 0.8f)
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
fun PhotoSelector(
    maxSelectionCount: Int = 5,
    screenWidth: Dp,
    screenHeight: Dp,
    onClickPhoto: (image: Bitmap) -> Unit,
    onClickPhotoIndex: (imageIndex: Int) -> Unit,
    thumbnailIndex: Int?,
    contentResolver: ContentResolver,
    onSetPhoto: (List<String>) -> Unit
) {
    var selectedImages by remember {
        mutableStateOf<List<Bitmap>>(emptyList())
    }
    val buttonText = "사진 선택"
    val multiplePhotoPickerLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickMultipleVisualMedia(
            maxItems = maxSelectionCount
        ), onResult = { uris ->
            uris.map { uriToBitmap(it, contentResolver) }.also { bitmapList ->
                selectedImages = bitmapList
                onSetPhoto(bitmapList.map { Formatter.convertBitmapToString(it) })
            }
        })

    fun launchPhotoPicker() {
        multiplePhotoPickerLauncher.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ImagesLayout(
            selectedImages = selectedImages,
            onClickPhoto = onClickPhoto,
            onClickIndex = onClickPhotoIndex,
            thumbnailIndex = thumbnailIndex,
            screenWidth = screenWidth,
            screenHeight = screenHeight
        )
        CommonButton(buttonText) { launchPhotoPicker() }
    }
}

@Composable
fun ProfilePhotoSelector(
    screenWidth: Dp,
    screenHeight: Dp,
    image: Bitmap?,
    contentResolver: ContentResolver,
    onSetPhoto: (String) -> Unit
) {
    var profileImage by remember { mutableStateOf(image) }
    val singlePhotoPickerLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri ->
                if (uri != null) {
                    uriToBitmap(uri, contentResolver).also {
                        profileImage = it
                        onSetPhoto(Formatter.convertBitmapToString(it))
                    }
                }
            })

    fun launchPhotoPicker() {
        singlePhotoPickerLauncher.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
    }

    Column(
        modifier = Modifier.size(screenWidth / 3, screenWidth / 3),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileImageLayout(image = profileImage,
            screenWidth = screenWidth,
            screenHeight = screenHeight,
            onClickPhoto = { launchPhotoPicker() })
    }

}

@Composable
fun ZoomableImage(image: Bitmap) {
    val scale = remember { mutableFloatStateOf(1f) }
    val offsetX = remember { mutableFloatStateOf(0f) }
    val offsetY = remember { mutableFloatStateOf(0f) }
    Box(modifier = Modifier
        .clip(RectangleShape)
        .fillMaxSize()
        .background(Color.Black)
        .offset { IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt()) }
        .pointerInput(Unit) {
            detectDragGestures { change, dragAmount ->
                offsetX.value += dragAmount.x
                offsetY.value += dragAmount.y
            }
        }) {
        AsyncImage(
            model = image, modifier = Modifier
                .align(Alignment.Center)
                .graphicsLayer(
                    scaleX = maxOf(.5f, minOf(3f, scale.value)),
                    scaleY = maxOf(.5f, minOf(3f, scale.value)),
                )
                .pointerInput(Unit) {
                    detectTransformGestures { centroid, pan, zoom, rotation ->
                        scale.floatValue *= zoom
                    }
                }, contentDescription = null
        )
    }
}

fun uriToBitmap(uri: Uri, contentResolver: ContentResolver): Bitmap {
    return ImageDecoder.decodeBitmap(ImageDecoder.createSource(contentResolver, uri))
}