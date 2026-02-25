package com.chuyou.base.http

import rxhttp.wrapper.annotation.DefaultDomain
import rxhttp.wrapper.annotation.Domain

@DefaultDomain
const val baseUrl = "https://www.wanandroid.com/"

@Domain(name = "xiaodian", className = "xiaodian")

const val D_API_URL = "https://appapi-ns1.xiaodianyouxi.com/index.php/"


const val GET_BANNER = "/banner/json"

const val GET_ARTICLE_LIST = "article/list/%s/json"

const val COLLECT_ARTICLE = "lg/collect/%s/json"
const val UN_COLLECT_ARTICLE = "lg/uncollect_originId/%s/json"
const val GET_QUESTION_ANSWER_LIST = "wenda/list/%s/json"


