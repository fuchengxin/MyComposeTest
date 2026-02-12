package com.chuyou.base.http

import android.app.Application
import okhttp3.OkHttpClient
import rxhttp.RxHttpPlugins
import java.util.concurrent.TimeUnit

object NetworkConfig {

    fun init(application: Application) {
        // 1. 设置自定义的 OkHttpClient
        val client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS) // 连接超时
            .readTimeout(10, TimeUnit.SECONDS)    // 读取超时
            .writeTimeout(10, TimeUnit.SECONDS)   // 写入超时
            // 如果需要忽略 Https 证书校验（仅限开发环境）
            // .sslSocketFactory(HttpsUtils.getSslSocketFactory().sSLSocketFactory, HttpsUtils.getSslSocketFactory().trustManager)
            // .hostnameVerifier { _, _ -> true }
            .build()

        // 2. 配置 RxHttp 全局参数
        RxHttpPlugins.init(client)
            .setDebug(true) // 开启调试模式，可以在 Logcat 看到详细的请求/响应日志（过滤 "RxHttp"）
            .setOnParamAssembly { param ->
                // 这里可以添加全局参数或 Header
                param.add("version", "1.0.0")
            }
    }


}