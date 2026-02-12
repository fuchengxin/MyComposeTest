package com.chuyou.mycomposetest.ui.web

import android.util.Log
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.chuyou.base.route.MyNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebScreen(
    url: String = "",
    title: String = "",
) {
    var webView by remember { mutableStateOf<WebView?>(null) }

    BackHandler(enabled = webView?.canGoBack() == true) {
        webView?.goBack()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = title, style = MaterialTheme.typography.titleMedium) },
                navigationIcon = {
                    IconButton(onClick = {
                        // 如果网页能回退就回退，不能回退就关闭页面
                        if (webView?.canGoBack() == true) {
                            webView?.goBack()
                        } else {
                            MyNavigator.back()
                        }
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "返回")
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
                        settings.javaScriptEnabled = true // 开启 JS 支持
                        settings.domStorageEnabled = true // 开启 DOM 存储（很多网页需要）

                        webViewClient = object : WebViewClient() {
                            override fun onPageFinished(view: WebView?, url: String?) {
                                super.onPageFinished(view, url)
                                Log.e("onPageFinished", "onPageFinished: $url")
                            }
                        }// 确保在内部打开网页，不跳浏览器

                        loadUrl(url)
                        webView = this // 将引用交给外部
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}