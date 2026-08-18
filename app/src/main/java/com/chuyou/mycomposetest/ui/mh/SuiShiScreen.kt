package com.chuyou.mycomposetest.ui.mh

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chuyou.base.page.BasePage

/**
 * 精致碎石锤计算
 *
 * 移植自 MHTools 的 SuiShiActivity。
 * 展示 1~16 级碎石锤所需金额（静态列表）。
 */
@Composable
fun SuiShiScreen() {
    val list = remember {
        listOf(
            "1级: 2.8w",
            "2级: 8w",
            "3级: 18.4w",
            "4级: 39.2w",
            "5级: 80.8w",
            "6级: 164w",
            "7级: 330w",
            "8级: 663w",
            "9级: 1328w",
            "10级: 2660w",
            "11级: 5322w",
            "12级: 1.06e",
            "13级: 2.13e",
            "14级: 4.26e",
            "15级: 8.5e",
            "16级: 17e"
        )
    }

    BasePage(title = "精致碎石锤计算", showBackButton = true) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(list) { item ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = item,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}
