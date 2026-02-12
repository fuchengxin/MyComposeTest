package com.chuyou.mycomposetest

import android.R.attr.handle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.chuyou.mycomposetest.route.MyNavigator
import com.chuyou.mycomposetest.route.RouteNavKey
import com.chuyou.mycomposetest.ui.home.HomeScreen
import com.chuyou.mycomposetest.ui.main.forMainScreen

@Composable
fun MyApp(modifier: Modifier = Modifier, appViewModel: AppViewModel) {
    val backStack = rememberNavBackStack(RouteNavKey.Main)
    val navigator = remember {
        MyNavigator(
            backStack = backStack,
            onNavigateToRestrictedKey = {
                RouteNavKey.Login(redirectToKey = it)
            },
            checkLogin = { appViewModel.isUserLoggedIn }
        )
    }
//    val currentKey = backStack.lastOrNull()
//    Box(modifier = Modifier.fillMaxSize()) {
//        if (currentKey != null) {
//            val provider = entryProvider {
//                forMainScreen(navigator)
//            }
//            provider.invoke(currentKey)
//        }
//    }
    Box(modifier = modifier.fillMaxSize().background(Color.Cyan)) {
        NavDisplay(
            modifier = Modifier.fillMaxSize(),
            backStack = backStack,
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = entryProvider {
                forMainScreen(navigator)
            }

        )
    }
}
