package com.chuyou.network.http

import com.chuyou.logger.LogUtils
import com.chuyou.network.bean.BaseResponse
import okhttp3.Response
import rxhttp.wrapper.annotation.Parser
import rxhttp.wrapper.exception.ParseException
import rxhttp.wrapper.parse.TypeParser
import rxhttp.wrapper.utils.Converter
import java.lang.reflect.Type

@Parser(name = "Response", wrappers = [BaseResponse::class])
class ResponseParser<T> : TypeParser<T> {
    constructor(type: Type) : super(type)

    override fun onParse(response: Response?): T {
        val data: BaseResponse<T> = Converter.convertTo(response, BaseResponse::class.java, *types)
        if (!data.isSuccess) {
            LogUtils.w("Response parse failed: code=${data.code}, message=${data.message}")
            throw ParseException(
                data.code.toString(),
                data.message ?: "请求失败",
                response
            )
        }

        @Suppress("UNCHECKED_CAST")
        return data.data as T
    }
}
