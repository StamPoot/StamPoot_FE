package com.example.footstamp.ui.components

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat.getString
import com.example.footstamp.R

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun CustomWebView(
    url: String,
    onResult: () -> Unit
) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                webViewClient = CustomWebViewClient(onResult)
                addJavascriptInterface(WebAppInterface(context), "Kakao")

                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
            }
        },
        update = { webView -> webView.loadUrl(url) }
    )
}

class CustomWebViewClient(val onResult: () -> Unit) : WebViewClient() {

    @Deprecated("Deprecated in Java")
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        url?.let {
            Log.d(TAG, "Token : $it")
//            if (it.startsWith("https://impine.shop/login/oauth2/code/kakao")) {
//                onResult()
//                return true
//            }
        }
        return false
    }
}

class WebAppInterface(private val context: Context) {
    @JavascriptInterface
    fun poseMessage() {

    }
}