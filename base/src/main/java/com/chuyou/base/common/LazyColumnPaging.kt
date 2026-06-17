package com.chuyou.base.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.chuyou.base.page.DefaultEmptyView
import com.chuyou.base.page.DefaultErrorView


@Composable
fun <T : Any> LazyColumnPaging(
    modifier: Modifier = Modifier,
    pagingItems: LazyPagingItems<T>,
    itemKey: ((T) -> Any),
    itemSpacing: Dp = 12.dp,
    contentPadding: PaddingValues = PaddingValues(12.dp),
    emptyView: @Composable () -> Unit = { DefaultEmptyView() },
    errorView: @Composable (Throwable) -> Unit = { throwable ->
        val errorMsg = when (throwable) {
            is java.net.SocketTimeoutException -> "网络请求超时，请稍后重试"
            is java.net.UnknownHostException -> "无法连接网络，请检查设置"
            else -> throwable.message ?: "未知错误"
        }
        DefaultErrorView(msg = errorMsg, throwable = throwable) { pagingItems.retry() }
    },
    itemContent: @Composable (T) -> Unit
) {
    val loadState = pagingItems.loadState
    when (// 首次加载中
        loadState.refresh) {
        is LoadState.Loading if pagingItems.itemCount == 0 -> {
            LoadingBox(modifier)
        }

        // 首次加载失败
        is LoadState.Error if pagingItems.itemCount == 0 -> {
            Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                errorView((loadState.refresh as LoadState.Error).error)
            }
        }

        // 空数据
        is LoadState.NotLoading if pagingItems.itemCount == 0 -> {
            Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                emptyView()
            }
        }

        // 正常内容显示
        else -> {
            PullToRefreshBox(
                modifier = modifier,
                isRefreshing = loadState.refresh is LoadState.Loading && pagingItems.itemCount > 0,
                onRefresh = { pagingItems.refresh() }
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(itemSpacing),
                    contentPadding = contentPadding,
                ) {
                    // 数据列表渲染
                    items(
                        count = pagingItems.itemCount,
                        key = pagingItems.itemKey { itemKey(it) }
                    ) { index ->
                        pagingItems[index]?.let { itemContent(it) }
                    }
                    //处理“底部加载更多”状态
                    if (pagingItems.itemCount > 0) {
                        item {
                            LoadMoreFooter(loadState = loadState.append) {
                                pagingItems.retry()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingBox(modifier: Modifier) {
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun LoadMoreFooter(loadState: LoadState, onRetry: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        when (loadState) {
            is LoadState.Loading -> {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CircularProgressIndicator(modifier = Modifier.size(18.dp), strokeWidth = 2.dp)
                    Spacer(Modifier.width(8.dp))
                    Text("正在加载...", fontSize = 14.sp, color = Color.Gray)
                }
            }

            is LoadState.Error -> {
                Text(
                    text = "加载失败，点击重试",
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable { onRetry() }
                )
            }

            is LoadState.NotLoading -> {
                if (loadState.endOfPaginationReached) {
                    Text("—— 已经到底啦 ——", fontSize = 14.sp, color = Color.LightGray)
                }
            }
        }
    }
}
