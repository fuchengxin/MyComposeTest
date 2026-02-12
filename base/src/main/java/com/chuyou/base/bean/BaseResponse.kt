package com.chuyou.base.bean

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("data") val data: T? = null,
    @SerializedName("errorMsg") val message: String? = null,
    @SerializedName("errorCode") val code: Int = 0
) {
    val isSuccess: Boolean
        get() = code == 0
}
