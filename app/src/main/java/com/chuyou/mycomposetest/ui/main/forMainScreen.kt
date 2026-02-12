package com.chuyou.mycomposetest.ui.main

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.chuyou.mycomposetest.route.MyNavigator
import com.chuyou.mycomposetest.route.RouteNavKey
import com.chuyou.mycomposetest.ui.home.HomeScreen

fun EntryProviderScope<NavKey>.forMainScreen(navigator: MyNavigator) {
    entry<RouteNavKey.Main> {
        MainScreen()
    }
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
) {
    val nestedBackStack = rememberNavBackStack(RouteNavKey.Main.Home)
    val selected = nestedBackStack.last()
    val context = LocalContext.current
    Scaffold(modifier = modifier.fillMaxSize(), bottomBar = {
        BottomNavBar(
            modifier = modifier, selectKey = selected, onClick = {
                if (!nestedBackStack.contains(it)) {
                    nestedBackStack.add(it)
                } else {
                    nestedBackStack.remove(it)
                    nestedBackStack.add(it)
                }
            })
    }) { innerPadding ->
        NavDisplay(
            modifier = Modifier.padding(innerPadding.calculateBottomPadding()).fillMaxSize().background(Color.Red),
            backStack = nestedBackStack,
            onBack = {
                if (selected == RouteNavKey.Main.Home) {
                    (context as Activity).finish()
                } else {
                    nestedBackStack.remove(selected)
                    nestedBackStack.add(selected)
                }
            },
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = entryProvider {
                entry<RouteNavKey.Main.Home> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        HomeScreen()
                    }
                }
            })

    }

}