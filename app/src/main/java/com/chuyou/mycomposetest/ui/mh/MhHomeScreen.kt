package com.chuyou.mycomposetest.ui.mh

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chuyou.base.page.BasePage
import com.chuyou.base.route.MyNavigator
import com.chuyou.base.route.RoutePath

/**
 * MH 工具箱入口
 *
 * 移植自 MHTools 的 MainActivity。
 * 列出宝石、星辉石、碎石锤、炼妖、宝宝修炼、体活等计算入口。
 */
@Composable
fun MhHomeScreen() {
    val items = listOf(
        "宝石计算" to { MyNavigator.navigate(RoutePath.MhBaoShi) },
        "星辉石计算" to { MyNavigator.navigate(RoutePath.MhXingHui) },
        "精致碎石锤计算" to { MyNavigator.navigate(RoutePath.MhSuiShi) },
        "炼妖资质计算" to { MyNavigator.navigate(RoutePath.MhLianYao) },
        "宝宝修炼" to { MyNavigator.navigate(RoutePath.MhBb) },
        "体活计算" to { MyNavigator.navigate(RoutePath.MhTiHuo) },
        "其它" to { MyNavigator.navigateToWeb("https://box.175dt.com/", "梦幻工具箱") }
    )

    BasePage(title = "MH工具箱", showBackButton = false, isStatusBarImmersive = true) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items) { (label, action) ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { action() }
                ) {
                    Text(
                        text = label,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 16.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}
