package com.example.footstamp.ui.components

import android.annotation.SuppressLint
import android.content.Context
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.footstamp.ui.theme.BlackColor

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun CustomWebView(
    url: String, onResult: (String) -> Unit
) {
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f),
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                webViewClient = CustomWebViewClient(onResult)
                addJavascriptInterface(WebAppInterface(context), "Kakao")

                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
            }
        }, update = { webView -> webView.loadUrl(url) })
}

class CustomWebViewClient(val onResult: (String) -> Unit) : WebViewClient() {

    @Deprecated("Deprecated in Java")
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        url?.let {
            if (it.startsWith("https://impine.shop/login/oauth2/code/kakao")) {
                onResult(it)
                return true
            }
        }
        return false
    }
}

class WebAppInterface(private val context: Context) {
    @JavascriptInterface
    fun poseMessage() {

    }
}