package com.chuyou.mycomposetest.ui.mine

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chuyou.base.page.BaseViewModelPage
import com.chuyou.base.route.MyNavigator

/**
 * 简单的登录/注册页面：顶部切换登录/注册，表单内容会随之变化。
 */
@Composable
fun LoginScreen(
    viewModel: AuthViewModel = viewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val isLogin = viewModel.isLoginMode

    BaseViewModelPage(
        viewModel = viewModel,
        title = if (isLogin) "登录" else "注册",
        showBackButton = true,
        isStatusBarImmersive = true,
        onBackClick = { MyNavigator.back() }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 32.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 标题和登录/注册切换
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (isLogin) "登录" else "注册",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (isLogin) "没有账号？点此注册" else "已有账号？点此登录",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.clickable {
                                viewModel.toggleMode()
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // 表单
                Column(
                        modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = state.username,
                        onValueChange = { viewModel.updateUsername(it) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        label = { Text("账号") }
                    )

                    OutlinedTextField(
                        value = state.password,
                        onValueChange = { viewModel.updatePassword(it) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        label = { Text("密码") },
                        visualTransformation = PasswordVisualTransformation()
                    )

                    if (!isLogin) {
                        OutlinedTextField(
                            value = state.repassword,
                            onValueChange = { viewModel.updateRepassword(it) },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            label = { Text("确认密码") },
                            visualTransformation = PasswordVisualTransformation()
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            if (isLogin) {
                                viewModel.login()
                            } else {
                                viewModel.register()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentPadding = PaddingValues(vertical = 12.dp)
                    ) {
                        Text(text = if (isLogin) "登录" else "注册")
                    }
                }
            }
        }
    }
}

