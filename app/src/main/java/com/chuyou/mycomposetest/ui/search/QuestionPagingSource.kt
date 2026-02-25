package com.chuyou.mycomposetest.ui.search

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chuyou.base.http.GET_QUESTION_ANSWER_LIST
import com.chuyou.base.http.getFlowRequest
import com.chuyou.mycomposetest.bean.BasePageData
import com.chuyou.mycomposetest.bean.QuestionAnswerItem
import kotlinx.coroutines.flow.first

class QuestionPagingSource() : PagingSource<Int, QuestionAnswerItem>() {

    override fun getRefreshKey(state: PagingState<Int, QuestionAnswerItem>): Int? = null
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, QuestionAnswerItem> {
        return try {
            val page = params.key ?: 1
            val response = getFlowRequest<BasePageData<QuestionAnswerItem>>(
                String.format(GET_QUESTION_ANSWER_LIST, page)
            ).first()

            LoadResult.Page(
                data = response.datas,
                prevKey = if (page <= 1) null else page - 1,
                nextKey = if (response.over) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}