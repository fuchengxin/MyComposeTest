package com.chuyou.mycomposetest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {
    private val _isReady = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()
    init {
        checkInitStatus()
    }
    private fun checkInitStatus() {
        viewModelScope.launch {
            // 模拟耗时操作，比如从数据库读 Token
            delay(2000)
            _isReady.value = true
        }
    }
}