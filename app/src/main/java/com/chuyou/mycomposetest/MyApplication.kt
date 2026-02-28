package com.chuyou.mycomposetest

import android.app.Application
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.chuyou.base.http.NetworkConfig
import com.tencent.mmkv.MMKV

class MyApplication : Application() {
    companion object {
        private var _instance: MyApplication? = null
        val instance: MyApplication get() = _instance!!
        val context: Context get() = instance.applicationContext
    }

    override fun onCreate() {
        super.onCreate()
        _instance = this
        if (isMainProcess()) {
            initSDKs()
        }
    }

    private fun initSDKs() {
        // 网络配置初始化
        NetworkConfig.init(this)
        // MMKV 初始化
        val rootDir = MMKV.initialize(this)
        Log.i("MMKV", "MMKV initialized in: $rootDir")
    }

    /**
     * 判断是否为主进程，避免多进程重复初始化带来的资源浪费
     */
    private fun isMainProcess(): Boolean {
        return packageName == getProcessName()
    }
}