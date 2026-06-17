package com.chuyou.mycomposetest.ui.search

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.chuyou.base.viewmodel.BaseViewModel

class QuestionViewModel : BaseViewModel<QuestionUiState>() {
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
}
