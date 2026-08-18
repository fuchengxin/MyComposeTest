package com.chuyou.mycomposetest.ui.mh

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.chuyou.base.page.BasePage
import com.chuyou.mycomposetest.util.MhCalcUtils

/**
 * 炼妖资质计算
 *
 * 移植自 MHTools 的 LianYaoActivity。
 * BB资质：主宠 + 副宠；BB成长：主宠 + 副宠。计算炼妖后的资质/成长可能结果。
 */
@Composable
fun LianYaoScreen() {
    val context = LocalContext.current
    // 资质系数表（0.70 ~ 1.10）
    val zzList = remember {
        listOf(
            "0.70", "0.72", "0.74", "0.76", "0.78",
            "0.80", "0.82", "0.84", "0.86", "0.88",
            "0.90", "0.92", "0.94", "0.96", "0.98",
            "1.00", "1.02", "1.04", "1.06", "1.08", "1.10"
        )
    }
    // 成长系数表
    val levelList = remember { listOf("0.96", "0.98", "1.00", "1.02") }

    var zz1 by remember { mutableStateOf("") }
    var zz2 by remember { mutableStateOf("") }
    var level1 by remember { mutableStateOf("") }
    var level2 by remember { mutableStateOf("") }
    var zzResult by remember { mutableStateOf("") }
    var levelResult by remember { mutableStateOf("") }

    BasePage(title = "炼妖计算", showBackButton = true) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // BB资质：主宠 + 副宠
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "BB资质: ",
                    style = MaterialTheme.typography.bodyLarge
                )
                OutlinedTextField(
                    value = zz1,
                    onValueChange = { zz1 = it.filter { ch -> ch.isDigit() } },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    label = { Text("主宠资质") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = zz2,
                    onValueChange = { zz2 = it.filter { ch -> ch.isDigit() || ch == '.' } },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    label = { Text("副宠资质") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
            }
            // BB成长：主宠 + 副宠
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "BB成长: ",
                    style = MaterialTheme.typography.bodyLarge
                )
                OutlinedTextField(
                    value = level1,
                    onValueChange = { level1 = it.filter { ch -> ch.isDigit() || ch == '.' } },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    label = { Text("主宠成长") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
                OutlinedTextField(
                    value = level2,
                    onValueChange = { level2 = it.filter { ch -> ch.isDigit() || ch == '.' } },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    label = { Text("副宠成长") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
            }
            Button(
                onClick = {
                    val z1 = zz1.trim()
                    val z2 = zz2.trim()
                    if (z1.isNotEmpty() && z2.isNotEmpty()) {
                        val totalZz = MhCalcUtils.add(z1, z2, 2)
                        val z = MhCalcUtils.div(totalZz, "2", 2)
                        val result = zzList.joinToString(", ") {
                            MhCalcUtils.subZeroAndDot(MhCalcUtils.mul(z, it, 2))
                        }
                        zzResult = "炼妖资质结果为：\n$result"
                    } else {
                        Toast.makeText(context, "请输入主宠和副宠资质", Toast.LENGTH_SHORT).show()
                    }
                    val l1 = level1.trim()
                    val l2 = level2.trim()
                    if (l1.isNotEmpty() && l2.isNotEmpty()) {
                        val totalLevel = MhCalcUtils.add(l1, l2, 3)
                        val l = MhCalcUtils.div(totalLevel, "2", 3)
                        val result = levelList.joinToString(", ") {
                            MhCalcUtils.subZeroAndDot(MhCalcUtils.mul(l, it, 3))
                        }
                        levelResult = "炼妖成长结果为：\n$result"
                    } else {
                        Toast.makeText(context, "请输入主宠和副宠成长", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) { Text("计算") }

            if (zzResult.isNotEmpty()) {
                Text(zzResult, style = MaterialTheme.typography.titleMedium)
            }
            if (levelResult.isNotEmpty()) {
                Text(levelResult, style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}
