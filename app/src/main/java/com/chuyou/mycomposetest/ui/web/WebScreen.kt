@file:Suppress("COMPOSE_APPLIER_CALL_MISMATCH")

package com.chuyou.mycomposetest.ui.web

import android.annotation.SuppressLint
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.chuyou.base.route.MyNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebScreen(
    url: String = "",
    title: String = "",
) {
    val webViewRef = remember { mutableStateOf<WebView?>(null) }

    // 处理物理返回�?
    BackHandler(enabled = webViewRef.value?.canGoBack() == true) {
        webViewRef.value?.goBack()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .basicMarquee(
                                initialDelayMillis = 1000,
                                repeatDelayMillis = 1000,
                                iterations = Int.MAX_VALUE,
                            )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        // 如果网页能回退就回退，不能回退就关闭页�?
                        if (webViewRef.value?.canGoBack() == true) {
                            webViewRef.value?.goBack()
                        } else {
                            MyNavigator.back()
                        }
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            AndroidView(
                factory = { context ->
                    WebView(context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        @SuppressLint("SetJavaScriptEnabled")
                        settings.javaScriptEnabled = true // 开�?JS 支持
                        settings.domStorageEnabled = true // 开�?DOM 存储（很多网页需要）

                        webViewClient = object : WebViewClient() {
                            override fun onPageFinished(view: WebView?, url: String?) {
                                super.onPageFinished(view, url)
                                Log.e("onPageFinished", "onPageFinished: $url")
                            }
                        }// 确保在内部打开网页，不跳浏览器

                        loadUrl(url)
                        webViewRef.value = this // 将引用交给外�?
                    }
                },
                modifier = Modifier.fillMaxSize(),
                onRelease = { view ->
                    view.apply {
                        stopLoading()
                        loadUrl("about:blank")
                        clearHistory()
                        removeAllViews()
                        destroy()
                    }
                    webViewRef.value = null
                }
            )
        }
    }
}