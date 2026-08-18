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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.chuyou.base.page.BasePage
import com.chuyou.mycomposetest.util.MhCalcUtils

/**
 * 体活计算
 *
 * 移植自 MHTools 的 TiHuoActivity。
 * 输入人物等级与技能等级，计算体活上限、每 5 分钟回复量及恢复满体活时间。
 */
@Composable
fun TiHuoScreen() {
    val context = LocalContext.current
    var level by remember { mutableStateOf("") }
    var skillLevel by remember { mutableStateOf("") }
    var numResult by remember { mutableStateOf("") }
    var levelResult by remember { mutableStateOf("") }
    var priceResult by remember { mutableStateOf("") }

    BasePage(title = "体活计算", showBackButton = true) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = level,
                onValueChange = { level = it.filter { ch -> ch.isDigit() } },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                label = { Text("人物等级") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            OutlinedTextField(
                value = skillLevel,
                onValueChange = { skillLevel = it.filter { ch -> ch.isDigit() } },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                label = { Text("技能等级") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Button(
                onClick = {
                    val levelStr = level.trim()
                    if (levelStr.isEmpty()) {
                        Toast.makeText(context, "请输入人物等级", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    val skillStr = skillLevel.trim()
                    if (skillStr.isEmpty()) {
                        Toast.makeText(context, "请输入技能等级", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    val personLevel = levelStr.toInt()
                    val skill = skillStr.toInt()
                    val num = personLevel * 5 + 50
                    val jNum = skill * 4
                    val total = num + jNum
                    numResult = "人物体活上限为: $total"
                    val s = total / 100 + personLevel / 50 + 2
                    levelResult = "每5分钟回复：$s"
                    val min = total / s
                    val y = if (total % s == 0) 0 else 5
                    val totalPrice = MhCalcUtils.add((min * 5).toString(), y.toString(), 2)
                    priceResult = "恢复满体活需要时间(不包含储备)：${MhCalcUtils.subZeroAndDot(totalPrice)}分钟"
                },
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) { Text("计算") }

            if (numResult.isNotEmpty()) Text(numResult, style = MaterialTheme.typography.titleMedium)
            if (levelResult.isNotEmpty()) Text(levelResult)
            if (priceResult.isNotEmpty()) Text(priceResult)
        }
    }
}
