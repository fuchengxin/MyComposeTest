package com.chuyou.base.http

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import rxhttp.RxHttp
import rxhttp.toAwaitResponse
import rxhttp.toFlowResponse


suspend inline fun <reified T> getRequest(url: String): T {
    return RxHttp.get(url)
        .toAwaitResponse<T>()
        .await()
}

suspend inline fun <reified T> postRequest(url: String, map: Map<String, String>? = null): T {
    return RxHttp.postForm(url)
        .apply {
            if (!map.isNullOrEmpty()) {
                addAll(map)
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

inline fun <reified T> postFlowRequest(url: String, map: Map<String, String>? = null): Flow<T> {
    return RxHttp.postForm(url)
        .apply {
            if (!map.isNullOrEmpty()) {
                addAll(map)
            }
        }
        .toFlowResponse<T>()
        .flowOn(Dispatchers.IO)
}
