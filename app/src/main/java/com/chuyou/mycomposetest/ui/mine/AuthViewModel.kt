package com.chuyou.mycomposetest.ui.mine

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.chuyou.base.effect.CommonEffect
import com.chuyou.base.route.MyNavigator
import com.chuyou.base.util.MMKVUtil
import com.chuyou.base.viewmodel.BaseViewModel
import com.chuyou.network.bean.BaseResponse
import com.chuyou.network.http.LOGIN
import com.chuyou.network.http.REGISTER
import com.chuyou.network.http.postFlowRequest

data class AuthUiState(
    val username: String = "",
    val password: String = "",
    val repassword: String = "",
    val isRegisterSuccess: Boolean = false,
)

class AuthViewModel : BaseViewModel<AuthUiState>() {

    var isLoginMode by mutableStateOf(true)
        private set

    override fun createInitialState(): AuthUiState = AuthUiState()

    fun toggleMode() {
        isLoginMode = !isLoginMode
        updateState {
            it.copy(
                password = "",
                repassword = "",
            )
        }
    }

    fun updateUsername(username: String) {
        updateState { it.copy(username = username) }
    }

    fun updatePassword(password: String) {
        updateState { it.copy(password = password) }
    }

    fun updateRepassword(repassword: String) {
        updateState { it.copy(repassword = repassword) }
    }

    /**
     * 登录
     * 使用 LOGIN 地址，postFlowRequest 发送参数 username/password
     */
    fun login() {
        val state = uiState.value
        if (state.username.isBlank() || state.password.isBlank()) {
            sendEffect(CommonEffect.ShowToast("请输入账号和密码"))
            return
        }
        val params = mapOf(
            "username" to state.username,
            "password" to state.password,
        )
        postFlowRequest<BaseResponse<Any>>(LOGIN, params)
            .request(
                isShowLoading = true,
                onSuccess = {
                    // 登录成功，保存本地登录状态
                    MMKVUtil.saveLoginState(state.username)
                    sendEffect(CommonEffect.ShowToast("登录成功"))
                    MyNavigator.back()
                }
            )
    }

    /**
     * 注册
     * 使用 REGISTER 地址，postFlowRequest 发送参数 username/password/repassword
     */
    fun register() {
        val state = uiState.value
        if (state.username.isBlank() || state.password.isBlank() || state.repassword.isBlank()) {
            sendEffect(CommonEffect.ShowToast("请输入完整信息"))
            return
        }
        if (state.password != state.repassword) {
            sendEffect(CommonEffect.ShowToast("两次密码不一致"))
            return
        }
        val params = mapOf(
            "username" to state.username,
            "password" to state.password,
            "repassword" to state.repassword,
        )
        postFlowRequest<BaseResponse<Any>>(REGISTER, params)
            .request(
                isShowLoading = true,
                onSuccess = {
                    updateState { it.copy(isRegisterSuccess = true) }
                    sendEffect(CommonEffect.ShowToast("注册成功"))
                    MyNavigator.back()
                }
            )
    }
}