package com.chuyou.base.http

import com.chuyou.base.bean.BaseResponse
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
        val t = data.data
        if (!data.isSuccess || t == null) {
            throw ParseException(
                data.code.toString(),
                data.message ?: "数据解析异常",
                response
            )
        }
        return t
    }
}