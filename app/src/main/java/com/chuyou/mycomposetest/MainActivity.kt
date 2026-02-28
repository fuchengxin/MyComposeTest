package com.chuyou.mycomposetest

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.chuyou.mycomposetest.ui.main.MainScreen
import com.chuyou.mycomposetest.ui.theme.MyComposeTestTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    private val splashViewModel: SplashViewModel by viewModels()
    private val appViewModel: AppViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        // 系统级启动屏不再额外延时，交给 Compose 页面做 3 秒倒计时
        splashScreen.setKeepOnScreenCondition { false }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }
        setContent {
            MyComposeTestTheme(dynamicColor = false) {
                AppRoot()
            }
        }
    }
}

@Composable
fun AppRoot() {
    var remainingSeconds by remember { mutableIntStateOf(3) }
    var showSplash by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        for (i in 3 downTo 1) {
            remainingSeconds = i
            delay(1000)
        }
        showSplash = false
    }

    if (showSplash) {
        SplashPage(remainingSeconds) {
            showSplash = false
        }
    } else {
        MainScreen()
    }
}

@Composable
fun SplashPage(remainingSeconds: Int, onSkip: () -> Unit) {
    val appName = stringResource(R.string.app_name)
    Surface(color = MaterialTheme.colorScheme.primary) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "欢迎使用$appName",
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 24.sp
            )
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
            ) {
                Button(onClick = onSkip) {
                    Text(text = "${remainingSeconds}s 跳过")
                }
            }
        }
    }
}