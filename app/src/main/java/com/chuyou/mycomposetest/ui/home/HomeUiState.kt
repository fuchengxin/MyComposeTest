package com.chuyou.mycomposetest.ui.home

import com.chuyou.mycomposetest.bean.ArticleItem
import com.chuyou.mycomposetest.bean.BannerItem

data class HomeUiState(
    val bannerItemList: List<BannerItem> = emptyList(),
    val articleList: List<ArticleItem> =  emptyList(),
    val content: String = "真实内容",
)