package com.chuyou.base.state

sealed class PageState {
    data object Loading : PageState()         // 加载中
    data object Content : PageState()         // 正常内容
    data object Empty : PageState()           // 空数据
    data class Error(val msg: String) : PageState() // 错误状态
}