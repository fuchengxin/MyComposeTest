package com.chuyou.base.widget

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.chuyou.base.R
import com.chuyou.base.util.toScaledDp
import com.chuyou.base.util.toScaledSp
import kotlinx.coroutines.delay


@Composable
fun commonAskPop(
    show: MutableState<Boolean>,//是否显示弹窗（可变参数）
    title: String? = null,//标题
    content: String? = null,//描述内容
    leftText: String? = null,//左边按钮文字
    rightText: String? = null,//右边按钮文字
    leftTextColor: Color = Color(0xFF757575),//左边文字默认颜色
    rightTextColor: Color = Color(0xFF183FD3),//右边文字默认颜色
    leftClick: () -> Unit = {},//点击左边按钮的回调
    rightClick: () -> Unit = {},//点击右边按钮的回调
    time: Int = 0,//倒计时间，单位:秒
    timeDownCallback: (Int) -> Unit = {},//每秒倒计时回调
    clickLeftDismiss: Boolean = true,//点击左边按钮是否关闭弹窗
    clickRightDismiss: Boolean = true,//点击右边按钮是否关闭弹窗
    onDismiss: () -> Unit={},
) {
    if (!show.value) return

    if (time > 0) {
        LaunchedEffect(time) {
            for (i in time - 1 downTo 0) {
                delay(1000)
                timeDownCallback(i)
            }
        }
    }
    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(color = colorResource(R.color.white))
        ) {
            if (title != null) {
                Text(
                    text = title,
                    fontSize = 16.sp.toScaledSp(),
                    color = colorResource(R.color.color_333333),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(
                        start = 34.dp.toScaledDp(),
                        top = 20.dp.toScaledDp(),
                        end = 34.dp.toScaledDp()
                    )
                )
            }

            if (content != null) {
                Text(
                    text = content,
                    fontSize = 16.sp.toScaledSp(),
                    color = colorResource(R.color.color_757575),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(
                        start = 12.dp.toScaledDp(),
                        end = 12.dp.toScaledDp(),
                        top = 15.dp.toScaledDp()
                    )
                )
            }

            HorizontalDivider(
                thickness = 0.5.dp.toScaledDp(),
                modifier = Modifier
                    .padding(top = 20.dp.toScaledDp())
                    .background(color = colorResource(R.color.color_E8E8E8))
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp.toScaledDp())
            ) {
                if (leftText != null) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clickable {
                                if (clickLeftDismiss) {
                                    show.value = false
                                }
                                leftClick()
                            }
                            .padding(horizontal = 8.dp.toScaledDp())
                    ) {
                        Text(
                            text = leftText,
                            fontSize = 15.sp.toScaledSp(),
                            color = leftTextColor
                        )
                    }
                }
                if (rightText != null) {
                    VerticalDivider(
                        thickness = 0.5.dp.toScaledDp(),
                        modifier = Modifier
                            .background(color = colorResource(R.color.color_E8E8E8))
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clickable {
                                if (clickRightDismiss) {
                                    show.value = false
                                }
                                rightClick()
                            }
                            .padding(horizontal = 8.dp.toScaledDp())
                    ) {
                        Text(
                            text = rightText,
                            fontSize = 15.sp.toScaledSp(),
                            color = rightTextColor
                        )
                    }

                }
            }
        }
    }

}
@Preview(backgroundColor = 0xffffffff, showBackground = true)
@Composable
private fun previewDialog() {
    val showDialog = remember {
        mutableStateOf(true)
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            "点击开关",
            color = colorResource(R.color.color_333333),
            modifier = Modifier
                .background(color = colorResource(R.color.white))
                .clickable {
                    showDialog.value = !showDialog.value
                })

        //倒计时5秒关闭
        val timeS = 5
        var timeStr by remember {
            mutableStateOf("右边${timeS}秒")
        }

        commonAskPop(
            showDialog,
            "标题标题标题标题标题标题标题标题标题标题标题标题",
            "内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容",
            "左边左边",
            timeStr,
            Color(0xFFF30011),
            Color(0xFF00CDA4),
            {
                Log.i("commonAskPop", "点击了左边左边")
            }, {
                Log.i("commonAskPop", "点击了右边")
            },
            timeS,
            { time ->
                if (time == 0) {
                    timeStr = "右边${timeS}秒"
                    showDialog.value = false
                    return@commonAskPop
                }
                timeStr = "右边${time}秒"
            },
            false,
            false
        )
    }

}
