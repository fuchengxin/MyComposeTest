package com.chuyou.logger

import com.elvishew.xlog.LogConfiguration
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog
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
            .build()
        val androidPrinter = AndroidPrinter(true)
        XLog.init(config,androidPrinter)
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
}
