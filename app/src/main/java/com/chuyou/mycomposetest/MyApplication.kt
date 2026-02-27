package com.chuyou.mycomposetest

import android.app.Application
import android.content.Context
import com.chuyou.base.http.NetworkConfig
import com.tencent.mmkv.MMKV

class MyApplication : Application() {
    companion object {
        lateinit var instance: MyApplication
            private set

        val context: Context
            get() = instance.applicationContext
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        NetworkConfig.init(this)
        MMKV.initialize(this)
    }
}