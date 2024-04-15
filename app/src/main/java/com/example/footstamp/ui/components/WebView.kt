package com.example.footstamp.ui.components

import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun CustomWebView(url: String) {
    AndroidView(factory = { context ->
        WebView(context).apply {
            webViewClient = webViewClient
            loadUrl(url)
        }
    })
}