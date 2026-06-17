package com.chuyou.network.http

import android.app.Application
import com.chuyou.logger.LogUtils
import com.chuyou.network.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okio.Buffer
import rxhttp.RxHttpPlugins
import java.util.concurrent.TimeUnit

object NetworkConfig {

    fun init(application: Application) {
        LogUtils.i("NetworkConfig init")

        val client = OkHttpClient.Builder()
            .addInterceptor(NetworkLogInterceptor())
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()

        RxHttpPlugins.init(client)
            .setDebug(BuildConfig.DEBUG)
            .setOnParamAssembly { param ->
                param.add("version", "1.0.0")
            }
    }
}

private class NetworkLogInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        logRequest(request)
        val startNs = System.nanoTime()

        return try {
            val response = chain.proceed(request)
            logResponse(response, elapsedMs = (System.nanoTime() - startNs) / 1_000_000)
            response
        } catch (throwable: Throwable) {
            LogUtils.e("HTTP FAILED: ${request.method} ${request.url}", throwable)
            throw throwable
        }
    }

    private fun logRequest(request: Request) {
        val url = request.url
        val queryParameters = buildString {
            for (i in 0 until url.querySize) {
                appendLine("${url.queryParameterName(i)}=${url.queryParameterValue(i).orEmpty()}")
            }
        }.ifBlank { "Empty" }

        LogUtils.i(
            buildString {
                appendLine("HTTP REQUEST")
                appendLine("method: ${request.method}")
                appendLine("url: $url")
                appendLine("headers:")
                appendLine(request.headers.ifBlank("Empty"))
                appendLine("query params:")
                append(queryParameters)
            }
        )
        LogUtils.e(buildString {
            appendLine("requestBody:")
            append(request.body.readBody())
        })
    }

    private fun logResponse(response: Response, elapsedMs: Long) {
        val responseBody = response.peekBody(MAX_LOG_BODY_BYTES).string().ifBlank { "Empty" }
        LogUtils.i(
            buildString {
                appendLine("HTTP RESPONSE")
                appendLine("url: ${response.request.url}")
                appendLine("code: ${response.code}")
                appendLine("message: ${response.message}")
                appendLine("elapsed: ${elapsedMs}ms")
                appendLine("headers:")
                append(response.headers.ifBlank("Empty"))
            }
        )
        LogUtils.w(buildString {
            appendLine("responseBody:")
            append(responseBody)
        })
    }

    private fun okhttp3.Headers.ifBlank(defaultValue: String): String {
        return toString().trim().ifBlank { defaultValue }
    }

    private fun RequestBody?.readBody(): String {
        if (this == null) return "Empty"
        if (isDuplex()) return "Duplex body is not readable"
        if (isOneShot()) return "One-shot body is not readable"

        return runCatching {
            val buffer = Buffer()
            writeTo(buffer)
            val contentType = contentType()
            val charset = contentType?.charset(Charsets.UTF_8) ?: Charsets.UTF_8
            if (!buffer.isProbablyUtf8()) {
                return@runCatching "Binary body omitted (${contentLength()} bytes)"
            }
            buffer.readString(charset).take(MAX_LOG_BODY_CHARS)
        }.getOrElse { throwable ->
            "Failed to read request body: ${throwable.message}"
        }
    }

    private fun Buffer.isProbablyUtf8(): Boolean {
        return runCatching {
            val prefix = Buffer()
            copyTo(prefix, 0, size.coerceAtMost(64))
            repeat(16) {
                if (prefix.exhausted()) return true
                val codePoint = prefix.readUtf8CodePoint()
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false
                }
            }
            true
        }.getOrDefault(false)
    }

    private companion object {
        const val MAX_LOG_BODY_BYTES = 1024L * 1024L
        const val MAX_LOG_BODY_CHARS = 20_000
    }
}
