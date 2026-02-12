package com.chuyou.mycomposetest

import android.content.Context.MODE_PRIVATE
import android.content.res.Configuration
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppViewModel : ViewModel() {

    private val sp by lazy {
        MyApplication.context.getSharedPreferences("MY_THEME", MODE_PRIVATE)
    }

    var isUserLoggedIn = false

    /**
     * 主题模式
     */
    private val _themeMode = MutableStateFlow(
        sp.getInt("THEME_MODE", Configuration.UI_MODE_NIGHT_YES)
    )
    val themeMode: StateFlow<Int> = _themeMode.asStateFlow()

    /**
     * 设置主题模式
     */
    fun setThemeMode(themeMode: Int) {
        _themeMode.value = themeMode
        sp.edit {
            putInt("THEME_MODE", themeMode)
        }
    }
}