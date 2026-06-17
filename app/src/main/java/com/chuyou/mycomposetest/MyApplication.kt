package com.chuyou.mycomposetest

import android.app.Application
import android.content.Context
import android.util.Log
import com.chuyou.logger.LogUtils
import com.chuyou.network.http.NetworkConfig
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
        }else{
            //  其他子进程
        }
    }

    private fun initSDKs() {
        LogUtils.init(enableLog = BuildConfig.DEBUG)
        NetworkConfig.init(this)
        val rootDir = MMKV.initialize(this)
        Log.i("MMKV", "MMKV initialized in: $rootDir")
        LogUtils.i("MMKV initialized in: $rootDir")
    }

    /**
     * 获取并判断当前是否为主进程
     */
    private fun isMainProcess(): Boolean {
        return packageName == getProcessName()
    }
}
