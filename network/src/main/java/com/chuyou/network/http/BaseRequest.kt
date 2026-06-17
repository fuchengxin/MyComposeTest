package com.chuyou.network.http

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import rxhttp.RxHttp
import rxhttp.toAwaitResponse
import rxhttp.toFlowResponse

fun cleanParams(map: Map<*, *>?): Map<String, String> {
    if (map.isNullOrEmpty()) return emptyMap()
    return map.mapNotNull { (key, value) ->
        val paramKey = key as? String
        val paramValue = value?.toString()
        if (paramKey.isNullOrBlank() || paramValue == null) null else paramKey to paramValue
    }.toMap()
}

suspend inline fun <reified T> getRequest(url: String): T {
    return RxHttp.get(url)
        .toAwaitResponse<T>()
        .await()
}

suspend inline fun <reified T> postRequest(url: String, map: Map<*, *>? = null): T {
    return RxHttp.postForm(url)
        .apply {
            val params = cleanParams(map)
            if (params.isNotEmpty()) {
                addAll(params)
            }
        }
        .toAwaitResponse<T>()
        .await()
}

inline fun <reified T> getFlowRequest(url: String): Flow<T> {
    return RxHttp.get(url)
        .toFlowResponse<T>()
        .flowOn(Dispatchers.IO)
}

inline fun <reified T> postFlowRequest(url: String, map: Map<*, *>? = null): Flow<T> {
    return RxHttp.postForm(url)
        .apply {
            val params = cleanParams(map)
            if (params.isNotEmpty()) {
                addAll(params)
            }
        }
        .toFlowResponse<T>()
        .flowOn(Dispatchers.IO)
}
