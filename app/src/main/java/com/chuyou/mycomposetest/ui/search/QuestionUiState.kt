package com.chuyou.mycomposetest.ui.search

import com.chuyou.mycomposetest.bean.QuestionAnswerItem

data class QuestionUiState (
    val questionAnswerItemList: List<QuestionAnswerItem> = emptyList()
)