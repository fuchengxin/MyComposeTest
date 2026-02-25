package com.chuyou.mycomposetest.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.chuyou.base.common.LazyColumnPaging
import com.chuyou.base.route.MyNavigator
import com.chuyou.mycomposetest.bean.QuestionAnswerItem


@Composable
fun QuestionScreen(
    viewModel: QuestionViewModel = viewModel()
) {
    val pagingItems = viewModel.questionPagingFlow.collectAsLazyPagingItems()
    Box(
        Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        LazyColumnPaging(
            modifier = Modifier.fillMaxSize(),
            pagingItems = pagingItems,
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
            itemKey = { it.id },
        ) { item ->
            QuestionAnswerItemView(item = item)
        }
    }
}

@Composable
fun QuestionAnswerItemView(
    item: QuestionAnswerItem,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .clickable {
                MyNavigator.navigateToWeb(url = item.link, title = item.title)
            }
            .padding(12.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {

        }
        Text(item.title)
    }
}
