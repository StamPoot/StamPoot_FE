package com.example.footstamp.ui.screen.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.footstamp.ui.base.BaseScreen
import com.example.footstamp.ui.screen.map.MapViewModel

@Composable
fun ProfileScreen() {
    BaseScreen(MapViewModel()) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            var count by remember {
                mutableStateOf(0)
            }
            Button(onClick = { count++ }) {

            }
            Text(text = "Profile $count")
        }
    }
}