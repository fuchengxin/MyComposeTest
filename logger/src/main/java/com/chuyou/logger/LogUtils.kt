package com.chuyou.logger

import com.elvishew.xlog.LogConfiguration
import com.elvishew.xlog.LogItem
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog
import com.elvishew.xlog.interceptor.Interceptor
import com.elvishew.xlog.printer.AndroidPrinter


object LogUtils {
    private const val DEFAULT_TAG = "log_test"
    private var initialized = false

    fun init(
        enableLog: Boolean = true,
        tag: String = DEFAULT_TAG,
    ) {
        val config = LogConfiguration.Builder()
            .logLevel(if (enableLog) LogLevel.ALL else LogLevel.NONE)
            .tag(tag)
            .enableStackTrace(2)
            .enableBorder()
            .addInterceptor(JsonSplitInterceptor())
            .build()
        val androidPrinter = AndroidPrinter(true)
        XLog.init(config, androidPrinter)
        initialized = true
    }

    fun v(message: String) {
        ensureInitialized()
        XLog.v(message)
    }

    fun d(message: String) {
        ensureInitialized()
        XLog.d(message)
    }

    fun i(message: String) {
        ensureInitialized()
        XLog.i(message)
    }

    fun w(message: String) {
        ensureInitialized()
        XLog.w(message)
    }

    fun e(message: String) {
        ensureInitialized()
        XLog.e(message)
    }

    fun e(message: String, throwable: Throwable) {
        ensureInitialized()
        XLog.e(message, throwable)
    }

    fun json(json: String) {
        ensureInitialized()
        XLog.json(json)
    }

    private fun ensureInitialized() {
        if (!initialized) {
            init()
        }
    }

    /**
     * 自定义长日志分段拦截器
     * 自动识别超过 3.5KB 的长日志
     * 加锁确保多线程并发时，长 JSON 不会被其他日志插队插得稀碎
     */
    private class JsonSplitInterceptor : Interceptor {
        private val MAX_LOG_LENGTH = 3500

        // 核心修正点：对齐 1.11.1 版本的 LogItem 签名
        override fun intercept(log: LogItem?): LogItem? {
            // 如果 log 为空或者消息为空，直接放行
            val message = log?.msg
            if (message != null && message.length > MAX_LOG_LENGTH) {
                synchronized(JsonSplitInterceptor::class.java) {
                    var i = 0
                    val length = message.length
                    var segmentIndex = 1

                    while (i < length) {
                        val end = length.coerceAtMost(i + MAX_LOG_LENGTH)
                        val part = message.substring(i, end)

                        val segmentTag = " [Part-$segmentIndex]"

                        // 使用 log.level 获取当前日志级别
                        when (log.level) {
                            LogLevel.VERBOSE -> XLog.tag(log.tag + segmentTag).v(part)
                            LogLevel.DEBUG -> XLog.tag(log.tag + segmentTag).d(part)
                            LogLevel.INFO -> XLog.tag(log.tag + segmentTag).i(part)
                            LogLevel.WARN -> XLog.tag(log.tag + segmentTag).w(part)
                            LogLevel.ERROR -> XLog.tag(log.tag + segmentTag).e(part)
                        }

                        i = end
                        segmentIndex++
                    }
                }
                return null // 砍碎并打印完后，返回 null 拦截掉原本的超长日志
            }
            return log // 短日志原样返回，正常打印
        }
    }
}
