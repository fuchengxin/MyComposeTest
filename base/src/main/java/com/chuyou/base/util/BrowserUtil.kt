package com.chuyou.base.util

import android.content.Context
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri

fun launchCustomChrome(context: Context, uri: String) {
    uri.let {
        val customTabsIntent = CustomTabsIntent.Builder().build()
        customTabsIntent.launchUrl(context, uri.toUri())
    }
}