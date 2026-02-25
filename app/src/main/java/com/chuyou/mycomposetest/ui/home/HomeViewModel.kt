package com.chuyou.mycomposetest.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.chuyou.base.bean.BaseResponse
import com.chuyou.base.effect.CommonEffect
import com.chuyou.base.http.COLLECT_ARTICLE
import com.chuyou.base.http.GET_ARTICLE_LIST
import com.chuyou.base.http.GET_BANNER
import com.chuyou.base.http.UN_COLLECT_ARTICLE
import com.chuyou.base.http.getFlowRequest
import com.chuyou.base.http.getRequest
import com.chuyou.base.http.postFlowRequest
import com.chuyou.base.viewmodel.BaseViewModel
import com.chuyou.mycomposetest.bean.ArticleItem
import com.chuyou.mycomposetest.bean.BannerItem
import com.chuyou.mycomposetest.bean.BasePageData

class HomeViewModel : BaseViewModel<HomeUiState>() {
    private var currentPage = 0
    var isEnd by mutableStateOf(false)
        private set

    init {
        loadData()
    }

    override fun createInitialState(): HomeUiState {
        return HomeUiState()
    }

    fun loadData() {
        loadBanner()
        loadArticleList()
    }

    private fun loadBanner() {
        launchRequestWithLoading(
            block = { getRequest<List<BannerItem>>(GET_BANNER) },
            isShowLoading = true,
            onSuccess = { list ->
                updateState { it.copy(bannerItemList = list) }
            }
        )
    }

    fun loadArticleList(isRefresh: Boolean = true) {
        if (isRefresh) {
            currentPage = 0
            isEnd = false
        } else {
            if (isEnd || isLoading.value) return
            currentPage++
        }
        getFlowRequest<BasePageData<ArticleItem>>(String.format(GET_ARTICLE_LIST, currentPage))
            .request(
                onSuccess = { data ->
                    val newList = data.datas
                    isEnd = data.over
                    updateState {
                        it.copy(
                            articleList = if (isRefresh) newList else it.articleList + newList
                        )
                    }
                },
                onError = {
                    if (!isRefresh) {
                        currentPage--
                    }
                })
    }

    fun toggleCollect(article: ArticleItem) {
        val isCollectAction = !article.collect
        val requestFlow = if (isCollectAction) {
            postFlowRequest<BaseResponse<Any?>>(String.format(COLLECT_ARTICLE, article.id))
        } else {
            postFlowRequest<BaseResponse<Any?>>(String.format(UN_COLLECT_ARTICLE, article.id))
        }
        requestFlow.request(
            isShowLoading = true,
            onSuccess = {
                updateState { state ->
                    val newList = state.articleList.toMutableList() // 转成可变列表
                    val index = newList.indexOfFirst { it.id == article.id }
                    if (index != -1) {
                        newList[index] = newList[index].copy(collect = isCollectAction)
                    }
                    state.copy(
                        articleList = newList
                    )
                }
                sendEffect(
                    CommonEffect.ShowToast(
                        if (isCollectAction) "收藏成功" else "取消收藏成功"
                    )
                )
            },
        )
    }

    fun collectArticle(articleId: Int) {
        postFlowRequest<BaseResponse<Any>>(String.format(COLLECT_ARTICLE, articleId))
            .request(
                isShowLoading = true,
                onSuccess = {
                    sendEffect(CommonEffect.ShowToast("收藏成功"))
                },
            )
    }

    fun unCollectArticle(articleId: Int) {
        postFlowRequest<BaseResponse<Any>>(String.format(UN_COLLECT_ARTICLE, articleId))
            .request(
                isShowLoading = true,
                onSuccess = {
                    sendEffect(CommonEffect.ShowToast("收藏成功"))
                },
            )
    }

}