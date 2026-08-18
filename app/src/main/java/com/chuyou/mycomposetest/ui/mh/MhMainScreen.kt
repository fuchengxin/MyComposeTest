package com.chuyou.mycomposetest.ui.mh

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.chuyou.base.route.MyNavigator
import com.chuyou.base.route.RoutePath
import com.chuyou.mycomposetest.route.mhGraph
import com.chuyou.mycomposetest.route.webGraph

/**
 * MH 工具箱独立宿主
 *
 * 作为一个新的入口，拥有独立的 NavHost，不走原有的 MainScreen。
 * 由启动页入口按钮进入，根页面返回时退出 Activity。
 */
@Composable
fun MhMainScreen() {
    val context = LocalContext.current
    val navController = rememberNavController()
    DisposableEffect(navController) {
        MyNavigator.setController(navController)
        onDispose {
            MyNavigator.setController(null)
        }
    }

    NavHost(
        navController = navController,
        startDestination = RoutePath.MhHome.route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(RoutePath.MhHome.route) {
            MhHomeScreen()
        }
        mhGraph()
        webGraph()
    }
}
