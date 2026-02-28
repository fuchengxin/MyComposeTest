package com.chuyou.base.route

import androidx.navigation.NavHostController
import java.lang.ref.WeakReference

object MyNavigator {
    private var navControllerRef: WeakReference<NavHostController>? = null
    private val navController: NavHostController?
        get() = navControllerRef?.get()
    
    // 添加防抖处理
    private var lastNavigationTime = 0L
    private const val NAVIGATION_DELAY = 500L // 500ms 防抖

    fun setController(controller: NavHostController?) {
        navControllerRef = controller?.let {
            WeakReference(it)
        }
    }

    fun navigate(route: RoutePath) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastNavigationTime > NAVIGATION_DELAY) {
            lastNavigationTime = currentTime
            navController?.navigate(route.route)
        }
    }

    fun navigateToWeb(url: String, title: String) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastNavigationTime > NAVIGATION_DELAY) {
            lastNavigationTime = currentTime
            // 关键：对 URL 进行转义，否则 URL 中的 / 或 ? 会破坏路由解析
            val route = RoutePath.Web.createRoute(url, title)
            navController?.navigate(route)
        }
    }

    fun back() {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastNavigationTime > NAVIGATION_DELAY) {
            lastNavigationTime = currentTime
            navController?.popBackStack()
        }
    }
}