package com.chuyou.mycomposetest.route

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed class RouteNavKey(val requiresLogin: Boolean = false) : NavKey {

    @Serializable
    data object Main : RouteNavKey() {
        @Serializable
        data object Home : RouteNavKey()

        @Serializable
        data object QuestionAnswer : RouteNavKey()

        @Serializable
        data object Navigation : RouteNavKey()

        @Serializable
        object Mine : RouteNavKey()
    }

    @Serializable
    data class Login(val redirectToKey: RouteNavKey? = null) : RouteNavKey()

    @Serializable
    data object Collect : RouteNavKey(requiresLogin = true) // 标记：收藏页需要登录

    @Serializable
    data object Search : RouteNavKey()
}