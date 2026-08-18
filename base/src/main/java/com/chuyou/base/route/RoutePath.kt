package com.chuyou.base.route

import android.net.Uri


sealed class RoutePath(val route: String){
    object Home : RoutePath("home")
    object Search : RoutePath("search")
    object Msg : RoutePath("msg")
    object Mine : RoutePath("mine")
    object Login : RoutePath("login")
    object Register : RoutePath("register")

    // MH 工具箱相关路由（独立入口，不走 MainScreen）
    object MhHome : RoutePath("mh_home")
    object MhBaoShi : RoutePath("mh_bao_shi")
    object MhXingHui : RoutePath("mh_xing_hui")
    object MhSuiShi : RoutePath("mh_sui_shi")
    object MhLianYao : RoutePath("mh_lian_yao")
    object MhBb : RoutePath("mh_bb")
    object MhTiHuo : RoutePath("mh_ti_huo")

    // 带参数的路由
    object Web : RoutePath("web?url={url}&title={title}") {
        /**
         * 封装一个创建路由的方法，外部调用时不需要关心拼接逻辑
         */
        fun createRoute(url: String, title: String): String {
            val encodedUrl = Uri.encode(url) // 💡 必须转码
            val encodedTitle = Uri.encode(title) // 标题建议也转码，防止中文乱码
            return "web?url=$encodedUrl&title=$encodedTitle"
        }
    }
}