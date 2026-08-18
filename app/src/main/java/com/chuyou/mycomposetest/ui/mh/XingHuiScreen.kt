package com.chuyou.mycomposetest.ui.mh

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.text.KeyboardOptions
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
 * 星辉石计算
 *
 * 移植自 MHTools 的 XingHuiActivity。
 * 输入 1 级星辉石价格与目标等级，计算目标星辉石价格。
 */
@Composable
fun XingHuiScreen() {
    val context = LocalContext.current
    var price by remember { mutableStateOf("") }
    var level by remember { mutableStateOf("") }
    var numResult by remember { mutableStateOf<Int?>(null) }
    var priceResult by remember { mutableStateOf<String?>(null) }

    BasePage(title = "星辉石计算", showBackButton = true) {
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
                label = { Text("1级星辉石价格") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
            OutlinedTextField(
                value = level,
                onValueChange = { level = it.filter { ch -> ch.isDigit() } },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                label = { Text("目标星辉石等级") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Button(
                onClick = {
                    val priceStr = price.trim()
                    if (priceStr.isEmpty()) {
                        Toast.makeText(context, "请输入1级星辉石价格", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    val lvStr = level.trim()
                    if (lvStr.isEmpty()) {
                        Toast.makeText(context, "请输入目标星辉石等级", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    val lv = lvStr.toDouble()
                    val num = 3.0.pow(lv - 1).toInt()
                    val totalPrice = MhCalcUtils.mul(priceStr, num.toString(), 2)
                    priceResult = MhCalcUtils.unitPrice(totalPrice)
                    numResult = num
                },
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) { Text("计算") }

            priceResult?.let { price ->
                Text(
                    buildAnnotatedString {
                        append("$level 级星辉石的价格：")
                        withStyle(SpanStyle(color = Color.Red)) { append(price) }
                    },
                    style = MaterialTheme.typography.titleMedium
                )
            }
            numResult?.let { num ->
                Text(buildAnnotatedString {
                    append("当前需要 ")
                    withStyle(SpanStyle(color = Color.Red)) { append(num.toString()) }
                    append(" 个1级星辉石")
                })
            }
        }
    }
}
