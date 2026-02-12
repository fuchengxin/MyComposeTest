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

inline fun <reified T> getFlowRequest(url: String): Flow<T> {
    return RxHttp.get(url)
        .toFlowResponse<T>()
        .flowOn(Dispatchers.IO)
}

inline fun <reified T> postFlowRequest(url: String): Flow<T> {
    return RxHttp.postJson(url)
        .toFlowResponse<T>()
        .flowOn(Dispatchers.IO)
}
