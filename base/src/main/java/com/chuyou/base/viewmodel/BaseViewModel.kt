package com.chuyou.base.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chuyou.base.effect.CommonEffect
import com.chuyou.base.page.PageState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<S> : ViewModel() {
    //是否显示页面loading
    var pageState by mutableStateOf<PageState>(PageState.Content)
        protected set

    //是否转圈
    var isLoading = mutableStateOf(false)
        protected set

    //是否正在请求接口数据
    var isLoadingData = mutableStateOf(false)
        protected set

    //UI 状态：使用 StateFlow 驱动 UI
    private val _uiState = MutableStateFlow( createInitialState())
    val uiState: StateFlow<S> =  _uiState.asStateFlow()

    // 单次副作用：如弹窗、跳转、Toast（使用 Channel 防止丢失）
    private val _effect = Channel<CommonEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()
    protected fun sendEffect(effect: CommonEffect) {
        viewModelScope.launch { _effect.send(effect) }
    }
    protected fun updateState(action: (S) -> S) {
        _uiState.update(action)
    }
    abstract fun createInitialState(): S
    /**
     * 核心请求封装
     * @param block 请求体，返回 IAwait<T>
     * @param isShowLoadingState 是否切换 PageState.Loading (通常用于全屏加载)
     * @param isShowLoading 是否切换 isLoading 弹窗 (通常用于按钮点击后的透明加载框)
     * @param onSuccess 成功回调
     * @param onError 失败回调 (如果不传，则走默认的错误处理)
     */
    protected fun <T> launchRequestWithLoading(
        block: suspend () -> T,
        isShowLoadingState: Boolean = false,
        isShowLoading: Boolean = false,
        onSuccess: ((T) -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null
    ) {
        viewModelScope.launch {
            if (isShowLoadingState) pageState = PageState.Loading
            if (isShowLoading) isLoading.value = true
            isLoadingData.value = true
            runCatching {
                block()
            }.onSuccess { result ->
                if (isShowLoadingState) pageState = PageState.Content
                if (result is List<*> && result.isEmpty()) {
                    pageState = PageState.Empty
                } else if (isShowLoadingState) {
                    pageState = PageState.Content
                }
                onSuccess?.invoke(result)
            }.onFailure { e ->
                handleError(e, isShowLoadingState, isShowLoading, onError)
            }.also {
                onComplete()
            }
        }
    }

    /**
     * 核心请求封装
     * @param isShowLoadingState 是否切换 PageState.Loading (通常用于全屏加载)
     * @param isShowLoading 是否切换 isLoading 弹窗 (通常用于按钮点击后的透明加载框)
     * @param onSuccess 成功回调
     * @param onError 失败回调 (如果不传，则走默认的错误处理)
     */
    protected fun <T> Flow<T>.request(
        isShowLoadingState: Boolean = false,
        isShowLoading: Boolean = false,
        onSuccess: (T) -> Unit = {},
        onError: ((Throwable) -> Unit)? = null
    ) {
        viewModelScope.launch {
            this@request
                .onStart {
                    if (isShowLoadingState) pageState = PageState.Loading
                    if (isShowLoading) isLoading.value = true
                    isLoadingData.value = true
                }
                .onCompletion {
                    onComplete()
                }
                .catch { e ->
                    handleError(e, isShowLoadingState, isShowLoading, onError)
                }
                .collect { data ->
                    if (isShowLoadingState) pageState = PageState.Content
                    if (data is List<*> && data.isEmpty()) {
                        pageState = PageState.Empty
                    } else if (isShowLoadingState) {
                        pageState = PageState.Content
                    }
                    onSuccess(data)
                }
        }
    }

    fun onComplete() {
        isLoading.value = false
        isLoadingData.value = false
    }

    private fun handleError(
        e: Throwable,
        isShowLoadingState: Boolean,
        isShowLoading: Boolean,
        onError: ((Throwable) -> Unit)?
    ) {
        e.printStackTrace()
        // 如果外部传入了 onError 则执行，否则走默认逻辑
        if (onError != null) {
            onError(e)
        } else {
            if (isShowLoadingState) {
                pageState = PageState.Error(e.message ?: "网络异常")
            }
            if (isShowLoading) {
                sendEffect(CommonEffect.ShowToast(e.message ?: "请求失败"))
            }
        }
    }

    protected fun showEmpty() {
        pageState = PageState.Empty
    }

    protected fun updatePageState(list: List<*>?) {
        pageState = if (list.isNullOrEmpty()) PageState.Empty else PageState.Content
    }
}