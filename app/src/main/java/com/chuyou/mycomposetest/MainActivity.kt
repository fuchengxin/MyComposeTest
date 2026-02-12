package com.chuyou.mycomposetest

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.chuyou.mycomposetest.ui.main.MainScreen
import com.chuyou.mycomposetest.ui.theme.MyComposeTestTheme

class MainActivity : ComponentActivity() {
    private val splashViewModel: SplashViewModel by viewModels()
    private val appViewModel: AppViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition {
            !splashViewModel.isReady.value
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }
        setContent {
            MyComposeTestTheme(dynamicColor = false) {
//                MyApp(appViewModel = appViewModel)
                MainScreen()
            }
        }
    }
}