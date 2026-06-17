package com.chuyou.mycomposetest.bean

import com.google.gson.annotations.SerializedName

/**
 * 对应旧项目 FragmentNav2 网格里的游戏项数据。
 */
data class MessageGameItem(
    @SerializedName("gameid") val gameId: String = "",
    @SerializedName("gamename") val gameName: String = "",
    @SerializedName("displayName") val displayName: String = "",
    @SerializedName("gameicon") val gameIcon: String = "",
)
