package com.chuyou.base.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DefaultErrorView(
    msg: String = "",
    throwable: Throwable? = null,
    onRetryClick: () -> Unit = {},
) {
    val errorText = when {
        msg.isNotBlank() -> msg
        throwable?.message?.isNotBlank() == true -> throwable.message
        else -> "未知错误，请稍后重试" // 🌟 默认提示语
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("$errorText", color = Color.Red)
        Button(onClick = onRetryClick, modifier = Modifier.padding(top = 8.dp)) {
            Text("点击重试")
        }
    }
}