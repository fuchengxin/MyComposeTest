package com.chuyou.mycomposetest.route

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.chuyou.base.route.RoutePath
import com.chuyou.base.route.rightToLeftAnimatedComposable
import com.chuyou.mycomposetest.ui.home.HomeScreen
import com.chuyou.mycomposetest.ui.message.MessageScreen
import com.chuyou.mycomposetest.ui.mine.LoginScreen
import com.chuyou.mycomposetest.ui.mine.MineScreen
import com.chuyou.mycomposetest.ui.mine.RegisterScreen
import com.chuyou.mycomposetest.ui.search.QuestionScreen
import com.chuyou.mycomposetest.ui.web.WebScreen


fun NavGraphBuilder.homeGraph(
) {
    composable(RoutePath.Home.route) {HomeScreen()}
    composable(RoutePath.Search.route) { QuestionScreen() }
    composable(RoutePath.Msg.route) { MessageScreen() }
    composable(RoutePath.Mine.route) { MineScreen() }
}

fun NavGraphBuilder.loginGraph(){

    // 登录页面
    rightToLeftAnimatedComposable(
        route = RoutePath.Login.route
    ) { LoginScreen() }

    // 注册页面
    rightToLeftAnimatedComposable(
        route = RoutePath.Register.route
    ) { RegisterScreen() }
}

fun NavGraphBuilder.webGraph(){
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
}