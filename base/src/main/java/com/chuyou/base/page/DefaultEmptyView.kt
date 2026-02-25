package com.chuyou.base.page

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun DefaultEmptyView() {
    Text("暂无数据", color = Color.Gray)
}