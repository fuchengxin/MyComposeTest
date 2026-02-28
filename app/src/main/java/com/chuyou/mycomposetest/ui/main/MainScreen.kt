package com.chuyou.mycomposetest.ui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.QuestionAnswer
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.chuyou.base.R
import com.chuyou.base.route.MyNavigator
import com.chuyou.base.route.RoutePath
import com.chuyou.base.route.bottomToTopAnimatedComposable
import com.chuyou.base.route.rightToLeftAnimatedComposable
import com.chuyou.base.util.resToSp
import com.chuyou.mycomposetest.ui.mine.LoginScreen
import com.chuyou.mycomposetest.ui.mine.RegisterScreen
import com.chuyou.mycomposetest.ui.web.WebScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    DisposableEffect(navController) {
        MyNavigator.setController(navController)
        onDispose {
            MyNavigator.setController(null)
        }
    }
    val items = listOf(RoutePath.Home.route, RoutePath.Search.route, RoutePath.Msg.route, RoutePath.Mine.route)
    val labels = listOf("首页", "问答", "消息", "我的")
    val icons = listOf(Icons.Outlined.Home, Icons.Outlined.QuestionAnswer, Icons.Outlined.Email, Icons.Outlined.Person)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val shouldShowBottomBar = currentRoute in items

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        containerColor = Color.Transparent,
        bottomBar = {
            // 使用动画控制导航栏的显示/隐藏
            AnimatedVisibility(
                visible = shouldShowBottomBar,
                enter = fadeIn(animationSpec = tween(durationMillis = 300)),
                exit = fadeOut(animationSpec = tween(durationMillis = 300))
            ) {
                NavigationBar {
                    items.forEachIndexed { index, route ->
                        NavigationBarItem(
                            icon = { Icon(icons[index], contentDescription = labels[index]) },
                            label = {
                                Text(
                                    labels[index],
                                    fontSize = R.dimen.isp_14.resToSp(),
                                    fontWeight = if (currentRoute == route) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedTextColor = Color.Red,
                                unselectedIconColor = Color.Gray,
                                selectedIconColor = Color.White,
                                indicatorColor = Color.Blue.copy(alpha = 0.1f)
                            ),
                            selected = currentRoute == route,
                            onClick = {
                                if (currentRoute != route) {
                                    navController.navigate(route) {
                                        // 避免返回栈堆积：回到首页，并弹出所有中间页面
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        // 避免重复点击同一个按钮创建多个实例
                                        launchSingleTop = true
                                        // 切换页面时保存状态（比如列表滑动的位置）
                                        restoreState = true
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        // 计算内容区域的padding
        val contentPadding = if (shouldShowBottomBar) {
            innerPadding.calculateBottomPadding()
        } else {
            0.dp
        }

        NavHost(
            navController = navController,
            startDestination = items[0],
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = contentPadding)
        ) {

            //首页,搜索，消息，我的
            homeGraph()
            // Web页面
            rightToLeftAnimatedComposable(
                route = RoutePath.Web.route,
                arguments = listOf(
                    navArgument("url") { type = NavType.StringType },
                    navArgument("title") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val url = backStackEntry.arguments?.getString("url") ?: ""
                val title = backStackEntry.arguments?.getString("title") ?: "网页"
                WebScreen(url, title)
            }

            // 登录页面
            rightToLeftAnimatedComposable(
                route = RoutePath.Login.route
            ) { LoginScreen() }
            
            // 注册页面
            rightToLeftAnimatedComposable(
                route = RoutePath.Register.route
            ) { RegisterScreen() }
        }
    }
}


