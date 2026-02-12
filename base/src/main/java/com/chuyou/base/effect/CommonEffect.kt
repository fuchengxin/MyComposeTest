package com.chuyou.base.effect

sealed class CommonEffect {
    data class ShowToast(val msg: String) : CommonEffect()
}