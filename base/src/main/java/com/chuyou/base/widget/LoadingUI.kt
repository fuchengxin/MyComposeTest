package com.chuyou.base.widget

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.chuyou.base.R

@Composable
fun LoadingUI(
    isShowLoading: Boolean,
    onDismiss: () -> Unit = {},
) {
    if (!isShowLoading) return
    Dialog(
        onDismissRequest = {
            onDismiss()
        },
    ) {
        Column(
            modifier = Modifier
                .height(115.dp)
                .width(150.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(color = colorResource(id = R.color.color_80000000)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // 图片旋转动画
            val infiniteTransition =
                rememberInfiniteTransition(label = "loading_spinner_transition")
            val rotation = infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 1000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                ), label = "rotation_animation"
            )
            Image(
                painter = painterResource(id = R.drawable.dialog_loading_img), // 替换为你的图片资源ID
                contentDescription = "加载中...",
                modifier = Modifier
                    .size(34.dp)
                    .rotate(rotation.value) // 应用旋转动画
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoadingUIPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        LoadingUI(true)
    }
}