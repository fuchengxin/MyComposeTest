package com.chuyou.mycomposetest.ui.mh

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.chuyou.base.page.BasePage

/**
 * 宝宝修炼
 *
 * 移植自 MHTools 的 BBActivity。
 * 展示宝宝修炼等级区间所需修炼果数量，以及各修炼等级所需经验。
 */
@Composable
fun BbScreen() {
    // 修炼果区间
    val fruitList = remember {
        listOf(
            "0-9: 35个修炼果",
            "0-10: 44个修炼果",
            "0-17: 162个修炼果",
            "0-20: 248个修炼果",
            "0-25: 452个修炼果",
            "10-20: 204个修炼果"
        )
    }

    // 修炼等级 -> 所需经验（1 级 150，每级 +20）
    val expList = remember {
        val result = mutableListOf<Pair<String, String>>()
        var start = 0
        val step = 20
        for (i in 1..25) {
            start = if (i == 1) 150 else start + step
            result.add(i.toString() to start.toString())
        }
        result
    }

    BasePage(title = "宝宝修炼", showBackButton = true) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Text(
                    text = "修炼果区间",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
            items(fruitList) { item ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = item,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            item {
                Text(
                    text = "修炼等级 / 所需经验",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
                )
            }
            item {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        Text("修炼等级", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
                        Text("所需经验", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
                    }
                }
            }
            items(expList) { (level, exp) ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        Text(level, modifier = Modifier.weight(1f))
                        Text(exp, modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}
