package com.chuyou.base.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

/**
 * 获取 Dp：用于设置间距、高度、宽度等
 * 对应原生：getResources().getDimension()
 */
@Composable
@ReadOnlyComposable
fun Int.resToDp(): Dp {
    return dimensionResource(id = this)
}

@Composable
@ReadOnlyComposable
fun Int.resToSp(): TextUnit {
    val dpValue = dimensionResource(id = this)
    return with(LocalDensity.current) { dpValue.toSp() }
}

/**
 * 将 Dimen 资源转换为数值 (忽略系统字体缩放，仅适配屏幕密度)
 */
@Composable
@ReadOnlyComposable
fun Int.resToSpRaw(): TextUnit {
    return dimensionResource(id = this).value.sp
}