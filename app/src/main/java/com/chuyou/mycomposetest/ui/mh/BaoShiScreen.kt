package com.chuyou.mycomposetest.ui.mh

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.chuyou.base.page.BasePage
import com.chuyou.mycomposetest.util.MhCalcUtils
import kotlin.math.pow

/**
 * 宝石计算
 *
 * 移植自 MHTools 的 BaoShiActivity。
 * 输入 1 级宝石价格与目标等级，计算目标宝石价格及 1~目标等级总需。
 */
@Composable
fun BaoShiScreen() {
    val context = LocalContext.current
    var price by remember { mutableStateOf("") }
    var level by remember { mutableStateOf("") }
    var numResult by remember { mutableStateOf<Int?>(null) }
    var totalResult by remember { mutableStateOf<String?>(null) }
    var priceResult by remember { mutableStateOf<String?>(null) }

    BasePage(title = "宝石计算") {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = price,
                onValueChange = { price = it.filter { ch -> ch.isDigit() || ch == '.' } },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                label = { Text("1级宝石价格") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
            OutlinedTextField(
                value = level,
                onValueChange = { level = it.filter { ch -> ch.isDigit() } },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                label = { Text("目标宝石等级") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Button(
                onClick = {
                    val priceStr = price.trim()
                    if (priceStr.isEmpty()) {
                        Toast.makeText(context, "请输入1级宝石价格", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    val lv = level.trim().toLongOrNull() ?: 0L
                    if (lv <= 0) {
                        Toast.makeText(context, "请输入目标宝石等级", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    var num = 2.0.pow((lv - 1).toInt()).toInt()
                    val totalPrice = MhCalcUtils.mul(priceStr, num.toString(), 2)
                    numResult = num
                    var totalPriceResult = 0.0
                    for (i in 1..lv) {
                        num = 2.0.pow((i - 1).toInt()).toInt()
                        val priceString = MhCalcUtils.mul(priceStr, num.toString(), 2)
                        totalPriceResult += MhCalcUtils.subZeroAndDot(priceString).toDouble()
                    }
                    totalResult = MhCalcUtils.unitPrice(totalPriceResult.toString())
                    priceResult = MhCalcUtils.subZeroAndDot(MhCalcUtils.unitPrice(totalPrice))
                },
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) { Text("计算") }

            priceResult?.let { price ->
                Text(
                    buildAnnotatedString {
                        append("$level 级宝石的价格：")
                        withStyle(SpanStyle(color = Color.Red)) {
                            append(price)
                        }
                    }
                )
            }
            numResult?.let { num ->
                Text(buildAnnotatedString {
                    append("当前需要 ")
                    withStyle(SpanStyle(color = Color.Red)) { append(num.toString()) }
                    append(" 个1级宝石")
                })
            }
            totalResult?.let { total ->
                Text(buildAnnotatedString {
                    append("1-$level 级总共需要 ")
                    withStyle(SpanStyle(color = Color.Red)) { append(total) }
                })
            }
        }
    }
}
