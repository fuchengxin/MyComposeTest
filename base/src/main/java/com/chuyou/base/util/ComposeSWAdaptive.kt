package com.chuyou.base.util

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.min

/**
 * dp,px,sp相互转换
 */

@Composable
fun dimenAsDp(resId: Int): Dp {
   return dimensionResource(resId)
}

@Composable
fun dimenAsSp(resId: Int): TextUnit {
    val context = LocalContext.current
    return with(LocalDensity.current) {
        (context.resources.getDimension(resId)).toSp()
    }
}

// 定义设计稿的基准宽度，例如 360dp
private const val BASE_SCREEN_WIDTH_DP = 375f

@Composable
fun Dp.toScaledDp(): Dp {
    val screenWidth = LocalConfiguration.current.screenWidthDp.toFloat()
    val scale = min(1365f, screenWidth) / BASE_SCREEN_WIDTH_DP
    return (this.value * scale).dp
}

fun toScaledDp(int: Dp, configuration: Configuration): Dp {
    val screenWidth = configuration.screenWidthDp.toFloat()
    val scale = min(1365f, screenWidth) / BASE_SCREEN_WIDTH_DP
    return (int.value * scale).dp
}

@Composable
fun TextUnit.toScaledSp(): TextUnit {
    val screenWidth = LocalConfiguration.current.screenWidthDp.toFloat()
    val scale = min(1365f, screenWidth) / BASE_SCREEN_WIDTH_DP
    return (this.value * scale).sp
}

fun toScaledSp(int: TextUnit, configuration: Configuration): TextUnit {
    val screenWidth = configuration.screenWidthDp.toFloat()
    val scale = min(1365f, screenWidth) / BASE_SCREEN_WIDTH_DP
    return (int.value * scale).sp
}