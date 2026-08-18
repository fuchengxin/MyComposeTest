package com.chuyou.mycomposetest.util

import java.math.BigDecimal
import java.math.RoundingMode

/**
 * MH 工具箱精确计算工具
 *
 * 移植自 MHTools 项目的 BigDecimalUtils / StringUtil 中的相关方法，
 * 用于宝石、星辉石、炼妖、体活等计算场景。
 */
object MhCalcUtils {

    private const val DEF_DIV_SCALE = 10

    /** 精确加法，保留 scale 位小数 */
    fun add(v1: String, v2: String, scale: Int): String {
        require(scale >= 0) { "保留的小数位数必须大于零" }
        val b1 = BigDecimal(v1)
        val b2 = BigDecimal(v2)
        return b1.add(b2).setScale(scale, RoundingMode.HALF_UP).toString()
    }

    /** 精确减法，保留 scale 位小数 */
    fun sub(v1: String, v2: String, scale: Int): String {
        require(scale >= 0) { "保留的小数位数必须大于零" }
        val b1 = BigDecimal(v1)
        val b2 = BigDecimal(v2)
        return b1.subtract(b2).setScale(scale, RoundingMode.HALF_UP).toString()
    }

    /** 精确乘法，保留 scale 位小数 */
    fun mul(v1: String, v2: String, scale: Int): String {
        require(scale >= 0) { "保留的小数位数必须大于零" }
        val b1 = BigDecimal(v1)
        val b2 = BigDecimal(v2)
        return b1.multiply(b2).setScale(scale, RoundingMode.HALF_UP).toString()
    }

    /** 精确除法，当除不尽时保留 scale 位小数 */
    fun div(v1: String, v2: String, scale: Int = DEF_DIV_SCALE): String {
        require(scale >= 0) { "保留的小数位数必须大于零" }
        val b1 = BigDecimal(v1)
        val b2 = BigDecimal(v2)
        return b1.divide(b2, scale, RoundingMode.HALF_UP).toString()
    }

    /** 精确减法（Double） */
    fun sub(v1: Double, v2: Double): Double {
        val b1 = BigDecimal(v1.toString())
        val b2 = BigDecimal(v2.toString())
        return b1.subtract(b2).toDouble()
    }

    /** 去掉多余的小数点和 0 */
    fun subZeroAndDot(originalStr: String?): String {
        if (originalStr.isNullOrEmpty()) return originalStr ?: ""
        var s = originalStr
        if (s.indexOf(".") > 0) {
            s = s.replace(Regex("0+?$"), "")
            s = s.replace(Regex("[.]$"), "")
        }
        return s
    }

    /**
     * 价格单位换算（输入价格以“万”为单位）：
     * 当价格 >= 10000 万（即 1 亿）时，除以 10000 并追加 "e"；否则追加 "w"。
     */
    fun unitPrice(price: String): String {
        val p = price.trim()
        if (p.isEmpty()) return ""
        val s = sub(p.toDouble(), 10000.0)
        return if (s >= 0) {
            subZeroAndDot(div(p, "10000", 4)) + "e"
        } else {
            subZeroAndDot(p) + "w"
        }
    }
}
