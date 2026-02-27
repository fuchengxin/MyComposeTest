package com.chuyou.base.route

import androidx.navigation.NavHostController
import java.lang.ref.WeakReference

object MyNavigator {
    private var navControllerRef: WeakReference<NavHostController>? = null
    private val navController: NavHostController?
        get() = navControllerRef?.get()

    fun setController(controller: NavHostController?) {
        navControllerRef = controller?.let {
            WeakReference(it)
        }
    }

    fun navigate(route: RoutePath) {
        navController?.navigate(route.route)
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