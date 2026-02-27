package com.chuyou.mycomposetest

import android.content.res.Configuration
import androidx.lifecycle.ViewModel
import com.chuyou.base.util.MMKVUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppViewModel : ViewModel() {

    var isUserLoggedIn = false

    /**
     * 主题模式
     */
    private val _themeMode = MutableStateFlow(
        MMKVUtil.getThemeMode(Configuration.UI_MODE_NIGHT_YES)
    )
    val themeMode: StateFlow<Int> = _themeMode.asStateFlow()

    /**
     * 设置主题模式
     */
    fun setThemeMode(themeMode: Int) {
        _themeMode.value = themeMode
        MMKVUtil.saveThemeMode(themeMode)
    }
}