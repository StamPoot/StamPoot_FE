package project.android.footstamp.ui.components

import android.annotation.SuppressLint
import android.content.Context
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import project.android.footstamp.R

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun CustomWebView(
    url: String, onResult: (String) -> Unit
) {
    val kakao = stringResource(R.string.kakao)
    val kakaoAuthStarts = stringResource(R.string.kakao_auth_starts)

    AndroidView(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.9f), factory = { context ->
        WebView(context).apply {
            settings.javaScriptEnabled = true
            webViewClient = CustomWebViewClient(kakaoAuthStarts, onResult)
            addJavascriptInterface(WebAppInterface(context), kakao)

            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
        }
    }, update = { webView -> webView.loadUrl(url) })
}

class CustomWebViewClient(private val authStartString: String, val onResult: (String) -> Unit) :
    WebViewClient() {

    @Deprecated("Deprecated in Java")
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        url?.let {
            if (it.startsWith(authStartString)) {
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