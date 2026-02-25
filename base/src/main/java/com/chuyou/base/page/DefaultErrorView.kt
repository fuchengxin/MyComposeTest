package com.chuyou.base.page

import androidx.compose.foundation.layout.Column
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
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("出错了: $msg", color = Color.Red)
        Text("出错了: ${throwable?.message}", color = Color.Red)
        Button(onClick = onRetryClick, modifier = Modifier.padding(top = 8.dp)) {
            Text("点击重试")
        }
    }
}