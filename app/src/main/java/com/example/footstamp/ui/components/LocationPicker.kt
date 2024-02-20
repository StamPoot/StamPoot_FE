package com.example.footstamp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.footstamp.data.util.SeoulLocation

@Composable
fun LocationPicker() {
    Spinner(
        items = SeoulLocation.entries,
        selectedItem = SeoulLocation.CENTRAL,
        onItemSelected = {},
        selectedItemFactory = { modifier, seoulLocation ->
            Column {
                Text(text = seoulLocation.location)
            }
        },
        dropdownItemFactory = { item, _ ->
            Text(text = item.location)
        }
    )
}