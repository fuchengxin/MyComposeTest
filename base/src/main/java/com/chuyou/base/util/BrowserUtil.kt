package com.chuyou.base.util

import android.content.Context
import android.content.Intent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri

//app内部打开一个浏览器标签来显示
fun launchCustomChrome(context: Context, uri: String) {
    uri.let {
        val customTabsIntent = CustomTabsIntent.Builder().build()
        customTabsIntent.launchUrl(context, uri.toUri())
    }
}

//启动第三方浏览器显示
fun launchExternalBrowser(context: Context, uri: String) {
    uri.let {
        val intent = Intent(Intent.ACTION_VIEW, uri.toUri())
        context.startActivity(intent)
    }
}