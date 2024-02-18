package com.example.footstamp.ui.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.footstamp.R
import com.example.footstamp.ui.theme.MainColor
import com.example.footstamp.ui.theme.SubColor

@Composable
fun ImagesLayout(selectedImages: List<Uri?>) {

    val itemWeight = LocalConfiguration.current.screenWidthDp.dp
    val itemHeight = LocalConfiguration.current.screenHeightDp.dp
    if (selectedImages.isEmpty()) PhotoItem(
        item = R.drawable.icon_circle_big,
        itemWeight = itemWeight,
        itemHeight = itemHeight
    ) else
        LazyRow {
            items(selectedImages) { item ->
                PhotoItem(item, itemWeight = itemWeight, itemHeight = itemHeight)
            }
        }
}

@Composable
fun PhotoItem(item: Uri?, itemWeight: Dp, itemHeight: Dp) {
    Box(
        modifier = Modifier
            .height(itemHeight / 3)
            .width(itemWeight.times(0.8f))
            .background(SubColor)
            .padding(start = itemWeight / 10, end = itemWeight / 10)
    ) {
        AsyncImage(
            model = item,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillWidth
        )
    }
}

@Composable
fun PhotoItem(item: Int, itemWeight: Dp, itemHeight: Dp) {
    Box(
        modifier = Modifier
            .height(itemHeight / 3)
            .width(itemWeight.times(0.8f))
            .background(MainColor)
            .padding(start = itemWeight / 10, end = itemWeight / 10)
    ) {
        AsyncImage(
            model = item,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun PhotoSelector(maxSelectionCount: Int = 5) {
    var selectedImages by remember {
        mutableStateOf<List<Uri?>>(emptyList())
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
        ImagesLayout(selectedImages = selectedImages)
        AddButton(buttonText) { launchPhotoPicker() }
    }
}