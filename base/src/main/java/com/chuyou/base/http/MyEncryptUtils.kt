package com.chuyou.base.http

import android.annotation.SuppressLint
import android.util.Base64
import android.util.Log
import java.net.URLEncoder
import java.security.Key
import java.security.MessageDigest
import java.util.TreeMap
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESKeySpec
import javax.crypto.spec.IvParameterSpec

fun encryptAES(originalJson: MutableMap<String, Any>) {

}

const val signKey = "Df1&#%\$WT9sGc%^urZO0!XkjglAv!Vel"
const val signKeyXdqy = "Df1&#%\$WT9sAv!Vel"
const val key = "qn%49E&E"
private const val ALGORITHM_DES = "DES/CBC/PKCS5Padding"

/**
 * 加密字符串
 */
fun encode(data: String?): String {
    if (data.isNullOrEmpty()) return ""
    return try {
        encode(data.toByteArray())
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

/**
 * 加密字节数组并进行 Base64 编码
 */
@SuppressLint("TrulyRandom")
@Throws(Exception::class)
fun encode(data: ByteArray): String {
    return try {
        val dks = DESKeySpec(key.toByteArray())
        val keyFactory = SecretKeyFactory.getInstance("DES")
        val secretKey: Key = keyFactory.generateSecret(dks)

        val cipher = Cipher.getInstance(ALGORITHM_DES)
        val iv = IvParameterSpec(key.toByteArray())

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv)
        val bytes = cipher.doFinal(data)

        // 使用 Android 的 Base64，NO_WRAP 表示不换行
        Base64.encodeToString(bytes, Base64.NO_WRAP)
    } catch (e: Exception) {
        throw Exception(e)
    }
}

/**
 * 解密字节数组
 */
@Throws(Exception::class)
fun decode(data: ByteArray): ByteArray {
    return try {
        val dks = DESKeySpec(key.toByteArray())
        val keyFactory = SecretKeyFactory.getInstance("DES")
        val secretKey: Key = keyFactory.generateSecret(dks)

        val cipher = Cipher.getInstance(ALGORITHM_DES)
        val iv = IvParameterSpec(key.toByteArray())

        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv)
        cipher.doFinal(data)
    } catch (e: Exception) {
        throw Exception(e)
    }
}

/**
 * 解密 Base64 字符串
 */
fun decodeValue(data: String?): String {
    if (data.isNullOrEmpty()) return ""
    return try {
        // 解码 Base64 得到密文字节，再进行 DES 解密
        val decodedBytes = Base64.decode(data, Base64.NO_WRAP)
        val originalBytes = decode(decodedBytes)
        String(originalBytes)
    } catch (e: Exception) {
        ""
    }
}

fun MD5(s: String): String? {
    val hexDigits = charArrayOf(
        '0', '1', '2', '3', '4', '5', '6',
        '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    )
    return try {
        val btInput = s.toByteArray()
        val mdInst = MessageDigest.getInstance("MD5")
        mdInst.update(btInput)
        val md = mdInst.digest()
        val j = md.size
        val str = CharArray(j * 2)
        var k = 0
        for (i in 0 until j) {
            val byte0 = md[i]
            str[k++] = hexDigits[byte0.toInt() ushr 4 and 0xf]
            str[k++] = hexDigits[byte0.toInt() and 0xf]
        }
        String(str)
    } catch (e: Exception) {
        // **[健壮性修复]** 使用统一的日志框架记录错误，而不是直接打印堆栈信息。
        Log.e("MD5", "Failed to generate MD5 hash.", e)
        null
    }
}

fun getSignKey(params: Map<String, String>): String? {
    // 使用 TreeMap 自动按 Key 升序排序
    val sortedParams = TreeMap<String, String>()
    params.forEach { (key, value) ->
        try {
            // URL 编码，并特殊处理星号 (与你提供的 Java 逻辑一致)
            val processedValue = if (value.isNotEmpty()) {
                URLEncoder.encode(value, "UTF-8").replace("*", "%2A")
            } else {
                ""
            }
            sortedParams[key] = processedValue
        } catch (e: Exception) {
            return null
        }
    }
    // 拼接字符串并加上密钥
    val signString = mapToString(sortedParams) + signKey
    Log.d("getSignKey", "String to be signed (before MD5): $signString")
    return MD5(signString)
}

// 辅助方法：将 Map 转为拼接字符串
fun mapToString(params: Map<String, String>): String {
    return params.entries.joinToString("&") { "${it.key}=${it.value}" }
}