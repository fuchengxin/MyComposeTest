package com.chuyou.base.http

import android.util.Log
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import rxhttp.wrapper.annotation.Param
import rxhttp.wrapper.param.JsonParam
import rxhttp.wrapper.param.Method
import java.net.URLEncoder
import java.util.TreeMap

@Param(methodName = "postEncryptForm")
class PostEncryptJsonParam : JsonParam {
    constructor(url: String) : super(url, Method.POST)

    override fun getRequestBody(): RequestBody {
        val allParams = bodyParam.toMutableMap()
        val stringParams = mutableMapOf<String, String>()
        allParams.forEach { (k, v) ->
            stringParams[k] = v?.toString() ?: ""
        }
        val fullSign = getSignKey(stringParams) ?: ""
        stringParams["sign"] = fullSign

        val sortedMap = TreeMap<String, String>()
        sortedMap.putAll(stringParams)

        val mapString = sortedMap.entries.joinToString("&") { "${it.key}=${it.value}" }
        val encryptedBase64 = encode(mapString)
        Log.e("PostEncryptJsonParam", "加密前: $mapString")
        val secondEncode = URLEncoder.encode(encryptedBase64, "UTF-8")
        val finalBody = "data=$secondEncode"
        return finalBody.toRequestBody("application/x-www-form-urlencoded; charset=utf-8".toMediaType())
    }

}