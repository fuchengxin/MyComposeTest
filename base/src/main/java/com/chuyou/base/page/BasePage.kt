package com.chuyou.base.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.chuyou.base.state.PageState
import com.chuyou.base.util.findActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasePage(
    title: String = "",
    state: PageState = PageState.Content, // 默认为内容态
    showBackButton: Boolean = true,
    onBackClick: (() -> Unit)? = null,
    onRetryClick: () -> Unit = {}, // 错误时的重试回调
    isStatusBarImmersive: Boolean = false,
    customTitle: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val defaultBackAction = {
        context.findActivity()?.finish()
    }
    Column(modifier = Modifier.fillMaxSize()) {
        if (title.isNotEmpty() || customTitle != null) {
            CenterAlignedTopAppBar(
                title = {
                    if (customTitle != null) customTitle() else Text(title)
                },
                navigationIcon = {
                    if (showBackButton) {
                        IconButton(onClick = { (onBackClick ?: defaultBackAction)() }) {
                            Icon(Icons.Default.ArrowBack, "返回")
                        }
                    }
                },
                // 关键：如果沉浸式，标题栏内部要避开状态栏；否则设为0
                windowInsets = if (isStatusBarImmersive) TopAppBarDefaults.windowInsets else WindowInsets(0)
            )
        } else {
            if (!isStatusBarImmersive) {
                // 非沉浸式：手动塞一个占位块，高度等于状态栏，把内容挤下去
                Spacer(Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
            }
        }
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                is PageState.Loading -> {
                    CircularProgressIndicator()
                }

                is PageState.Empty -> {
                    Text("暂无数据", color = Color.Gray)
                }

                is PageState.Error -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("出错了: ${state.msg}", color = Color.Red)
                        Button(onClick = onRetryClick, modifier = Modifier.padding(top = 8.dp)) {
                            Text("点击重试")
                        }
                    }
                }

                is PageState.Content -> {
                    content()
                }
            }
        }
    }
}