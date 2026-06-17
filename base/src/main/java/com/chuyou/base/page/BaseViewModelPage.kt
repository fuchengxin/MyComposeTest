package com.chuyou.base.page

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.chuyou.base.effect.CommonEffect
import com.chuyou.base.effect.ObserveEffect
import com.chuyou.base.viewmodel.BaseViewModel
import com.chuyou.base.widget.LoadingUI

@Composable
fun <S> BaseViewModelPage(
    viewModel: BaseViewModel<S>,
    title: String = "",
    showBackButton: Boolean = true,
    isStatusBarImmersive: Boolean = true,
    onBackClick: (() -> Unit)? = null,
    onRetry: () -> Unit = {},
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    ObserveEffect(viewModel.effect) { effect ->
        when (effect) {
            is CommonEffect.ShowToast -> {
                Toast.makeText(context, effect.msg, Toast.LENGTH_SHORT).show()
            }
        }
    }
    BasePage(
        title = title,
        state = viewModel.pageState,
        showBackButton = showBackButton,
        onBackClick = onBackClick,
        onRetryClick = onRetry,
        isStatusBarImmersive = isStatusBarImmersive,
        content = {
            Box(modifier = Modifier.fillMaxSize()) {
                content()
                LoadingUI(
                    isShowLoading = viewModel.isLoading.value,
                    onDismiss = { viewModel.isLoading.value = false }
                )
            }
        }
    )
}