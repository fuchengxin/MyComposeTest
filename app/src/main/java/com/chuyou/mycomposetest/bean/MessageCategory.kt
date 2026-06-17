package com.chuyou.mycomposetest.bean

import com.google.gson.annotations.SerializedName

/**
 * 对应旧项目 FragmentNav2 里的分类数据。
 * select 不是接口字段，而是 Compose 页面本地用来标记当前选中项的状态。
 */
data class MessageCategory(
    @SerializedName("id") val id: String = "",
    @SerializedName("name") val name: String = "",
    val select: Boolean = false,
)
