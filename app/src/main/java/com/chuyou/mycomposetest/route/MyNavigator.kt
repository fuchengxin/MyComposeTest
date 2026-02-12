package com.chuyou.mycomposetest.route

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

class MyNavigator(
    private val backStack: NavBackStack<NavKey>,
    private val onNavigateToRestrictedKey: (targetKey: RouteNavKey?) -> RouteNavKey,
    private val checkLogin: () -> Boolean
) {
    fun goTo(target: RouteNavKey) {
        // 核心拦截逻辑
        if (target.requiresLogin && !checkLogin()) {
            println("拦截：用户未登录，去登录页")
            val loginKey = onNavigateToRestrictedKey(target)
            backStack.add(loginKey)
        } else {
            backStack.add(target)
        }
    }

    fun goBack() {
        backStack.removeLastOrNull()
    }

    fun goBack(routeNavKey: RouteNavKey) {
        backStack.remove(routeNavKey)
    }
}