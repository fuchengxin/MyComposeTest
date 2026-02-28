package com.chuyou.base.util

import com.tencent.mmkv.MMKV

object MMKVUtil {
    private val mmkv by lazy {
        MMKV.defaultMMKV()
    }

    // 常量定义
    const val KEY_IS_LOGGED_IN = "IS_LOGGED_IN"
    const val KEY_USERNAME = "USERNAME"
    const val KEY_THEME_MODE = "THEME_MODE"

    /**
     * 存储数据（通用方法）
     */
    fun <T> put(key: String, value: T) {
        when (value) {
            is Boolean -> mmkv.encode(key, value)
            is String -> mmkv.encode(key, value)
            is Int -> mmkv.encode(key, value)
            is Long -> mmkv.encode(key, value)
            is Float -> mmkv.encode(key, value)
            is Double -> mmkv.encode(key, value)
        }
    }

    /**
     * 读取数据（通用方法）
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: String, defaultValue: T): T {
        return when (defaultValue) {
            is Boolean -> mmkv.decodeBool(key, defaultValue) as T
            is String -> (mmkv.decodeString(key, defaultValue) ?: defaultValue) as T
            is Int -> mmkv.decodeInt(key, defaultValue) as T
            is Long -> mmkv.decodeLong(key, defaultValue) as T
            is Float -> mmkv.decodeFloat(key, defaultValue) as T
            is Double -> mmkv.decodeDouble(key, defaultValue) as T
            else -> defaultValue
        }
    }


    /**
     * 删除键值对
     */
    fun remove(key: String) {
        mmkv.removeValueForKey(key)
    }

    /**
     * 批量删除键值对
     */
    fun remove(vararg keys: String) {
        mmkv.removeValuesForKeys(keys)
    }

    /**
     * 清空所有数据
     */
    fun clear() {
        mmkv.clearAll()
    }

    // 登录状态相关

    /**
     * 保存登录状态
     */
    fun saveLoginState(username: String) {
        put(KEY_IS_LOGGED_IN, true)
        put(KEY_USERNAME, username)
    }

    /**
     * 获取登录状态
     */
    fun isLoggedIn(): Boolean {
        return get(KEY_IS_LOGGED_IN, false)
    }

    /**
     * 获取当前用户名
     */
    fun getUsername(): String {
        return get(KEY_USERNAME, "")
    }

    /**
     * 清除登录状态
     */
    fun clearLoginState() {
        remove(KEY_IS_LOGGED_IN, KEY_USERNAME)
    }

    // 主题模式相关

    /**
     * 保存主题模式
     */
    fun saveThemeMode(themeMode: Int) {
        put(KEY_THEME_MODE, themeMode)
    }

    /**
     * 获取主题模式
     */
    fun getThemeMode(defaultValue: Int): Int {
        return get(KEY_THEME_MODE, defaultValue)
    }
}
