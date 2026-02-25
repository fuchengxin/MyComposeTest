package com.chuyou.mycomposetest.ui.search

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.chuyou.base.effect.CommonEffect
import com.chuyou.base.http.GET_QUESTION_ANSWER_LIST
import com.chuyou.base.http.getFlowRequest
import com.chuyou.base.viewmodel.BaseViewModel
import com.chuyou.mycomposetest.bean.BasePageData
import com.chuyou.mycomposetest.bean.QuestionAnswerItem

class QuestionViewModel : BaseViewModel<QuestionUiState>() {
    private var currentPage = 0
    val questionPagingFlow = Pager(
        config = PagingConfig(
            pageSize = 20,
            prefetchDistance = 2,
            initialLoadSize = 20
        ),
        pagingSourceFactory = { QuestionPagingSource() }
    ).flow.cachedIn(viewModelScope)

    override fun createInitialState(): QuestionUiState {
        return QuestionUiState()
    }

    init {
        loadData()
    }

    fun loadData() {
        getFlowRequest<BasePageData<QuestionAnswerItem>>(String.format(GET_QUESTION_ANSWER_LIST, currentPage))
            .request(
                onSuccess = { data ->
                    sendEffect(CommonEffect.ShowToast("请求成功"))
                    updateState { it.copy(questionAnswerItemList = data.datas) }
                },
            )
    }
}