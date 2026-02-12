package com.chuyou.base.route

import androidx.navigation.NavHostController

object MyNavigator {
    private var navController: NavHostController? = null
    fun setController(controller: NavHostController?) {
        navController = controller
    }

    fun navigateToWeb(url: String, title: String) {
        // 关键：对 URL 进行转义，否则 URL 中的 / 或 ? 会破坏路由解析
        val route = RoutePath.Web.createRoute(url, title)
        navController?.navigate(route)
    }

    fun back() {
        navController?.popBackStack()
    }

    fun navigateToSearch() {
        navController?.navigate(RoutePath.Search)
    }
}