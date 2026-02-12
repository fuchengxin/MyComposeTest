package com.chuyou.base.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.chuyou.base.effect.CommonEffect
import com.chuyou.base.effect.ObserveEffect
import com.chuyou.base.viewmodel.BaseViewModel
import com.chuyou.base.widget.LoadingUI
import kotlinx.coroutines.launch

@Composable
fun <S, E> BaseViewModelPage(
    viewModel: BaseViewModel<S, E>,
    title: String = "",
    showBackButton: Boolean = true,
    isStatusBarImmersive: Boolean = false,
    onBackClick: (() -> Unit)? = null,
    onRetry: () -> Unit = {},
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    ObserveEffect(viewModel.effect) { effect ->
        when (effect) {
            is CommonEffect.ShowToast -> {
//                Toast.makeText(context, effect.msg, Toast.LENGTH_SHORT).show()
                scope.launch {
                    snackBarHostState.showSnackbar(
                        message = effect.msg,
                    )
                }
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