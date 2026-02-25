package com.chuyou.mycomposetest.ui.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.chuyou.base.route.RoutePath
import com.chuyou.mycomposetest.ui.home.HomeScreen
import com.chuyou.mycomposetest.ui.message.MessageScreen
import com.chuyou.mycomposetest.ui.mine.MineScreen
import com.chuyou.mycomposetest.ui.search.QuestionScreen

fun NavGraphBuilder.homeGraph(
) {
    composable(RoutePath.Home.route) {HomeScreen()}
    composable(RoutePath.Search.route) { QuestionScreen() }
    composable(RoutePath.Msg.route) { MessageScreen() }
    composable(RoutePath.Mine.route) { MineScreen() }
}