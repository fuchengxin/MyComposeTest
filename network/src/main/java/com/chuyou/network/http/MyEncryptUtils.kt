package com.chuyou.network.http

import java.net.URLEncoder
import java.security.Key
import java.security.MessageDigest
import java.util.Base64
import java.util.TreeMap
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESKeySpec
import javax.crypto.spec.IvParameterSpec

const val signKey = "Df1&#%\$WT9sGc%^urZO0!XkjglAv!Vel"
const val signKeyXdqy = "Df1&#%\$WT9sAv!Vel"
const val key = "qn%49E&E"

private const val ALGORITHM_DES = "DES/CBC/PKCS5Padding"

fun encode(data: String?): String {
    if (data.isNullOrEmpty()) return ""
    return encode(data.toByteArray())
}

fun encode(data: ByteArray): String {
    val secretKey = createSecretKey()
    val cipher = Cipher.getInstance(ALGORITHM_DES)
    cipher.init(Cipher.ENCRYPT_MODE, secretKey, IvParameterSpec(key.toByteArray()))
    return Base64.getEncoder().encodeToString(cipher.doFinal(data))
}

fun decode(data: ByteArray): ByteArray {
    val secretKey = createSecretKey()
    val cipher = Cipher.getInstance(ALGORITHM_DES)
    cipher.init(Cipher.DECRYPT_MODE, secretKey, IvParameterSpec(key.toByteArray()))
    return cipher.doFinal(data)
}

fun decodeValue(data: String?): String {
    if (data.isNullOrEmpty()) return ""
    return runCatching {
        String(decode(Base64.getDecoder().decode(data)))
    }.getOrDefault("")
}

fun MD5(s: String): String {
    val digest = MessageDigest.getInstance("MD5").digest(s.toByteArray())
    return digest.joinToString("") { "%02X".format(it) }
}

fun getSignKey(params: Map<String, String>): String {
    return createSign(params, signKey)
}

fun mapToString(params: Map<String, String>): String {
    return params.entries.joinToString("&") { "${it.key}=${it.value}" }
}

fun createEncryptedFormData(params: Map<String, String>): String {
    return URLEncoder.encode(encode(mapToString(params)), Charsets.UTF_8.name())
}

private fun createSign(params: Map<String, String>, secret: String): String {
    val sortedParams = TreeMap<String, String>()
    params.forEach { (key, value) ->
        sortedParams[key] = if (value.isNotEmpty()) {
            URLEncoder.encode(value, Charsets.UTF_8.name()).replace("*", "%2A")
        } else {
            ""
        }
    }
    return MD5(mapToString(sortedParams) + secret)
}

private fun createSecretKey(): Key {
    val dks = DESKeySpec(key.toByteArray())
    val keyFactory = SecretKeyFactory.getInstance("DES")
    return keyFactory.generateSecret(dks)
}
