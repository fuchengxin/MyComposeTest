package com.chuyou.mycomposetest.ui.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.chuyou.base.page.BaseViewModelPage
import com.chuyou.base.route.MyNavigator
import com.chuyou.base.util.launchCustomChrome
import com.chuyou.mycomposetest.bean.ArticleItem
import com.chuyou.mycomposetest.bean.BannerItem
import kotlinx.coroutines.delay


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    val isRefreshing = viewModel.isLoading.value
    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItemIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            val totalItemsCount = listState.layoutInfo.totalItemsCount
            // 阈值：距离底部还有 2 个条目时就开始预加载
            lastVisibleItemIndex >= totalItemsCount - 2 && totalItemsCount > 0
        }
    }

    // 当满足加载条件且当前不在加载中时，触发请求
    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value && !viewModel.isLoading.value) {
            viewModel.loadArticleList(isRefresh = false)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        BaseViewModelPage(
            viewModel = viewModel,
            title = "",
            showBackButton = true,
            isStatusBarImmersive = true,
            onRetry = { viewModel.loadData() }
        ) {
            PullToRefreshBox(
                modifier = Modifier.fillMaxSize(),
                isRefreshing = isRefreshing,
                onRefresh = {
                    viewModel.loadArticleList(isRefresh = true)
                }) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    //顶部banner
                    item {
                        BannerComponent(
                            bannerList = state.bannerItemList,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(6.dp))
                    }
                    //文章列表
                    items(state.articleList, key = { it.id }) { article ->
                        ArticleItemView(
                            articleItem = article,
                            onCollectClick = { articleItem ->
                                viewModel.toggleCollect(articleItem)
                            })
                    }
                    //加载更多
                    item {
                        FootView(
                            isLoading = viewModel.isLoadingData.value,
                            isEnd = viewModel.isEnd,
                            itemCount = state.articleList.size
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ArticleItemView(
    articleItem: ArticleItem,
    onArticleItemClick: (ArticleItem) -> Unit = {},
    onCollectClick: (ArticleItem) -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, top = 6.dp, end = 12.dp, bottom = 12.dp)
            .clickable {
//                onArticleItemClick(articleItem)
                MyNavigator.navigateToWeb(url = articleItem.link, title = articleItem.title)
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
                .padding(12.dp)
        ) {

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = articleItem.shareUser.ifBlank { articleItem.author },
                    modifier = Modifier.weight(1f),
                    color = Color.Black,
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = articleItem.niceShareDate,
                    color = Color.DarkGray,
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 12.sp,
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = articleItem.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.labelSmall,
                color = Color.DarkGray,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = "${articleItem.chapterName} & ${articleItem.superChapterName}",
                style = MaterialTheme.typography.labelSmall,
                color = Color.DarkGray,
                modifier = Modifier.padding(end = 40.dp)
            )
        }
        IconButton(
            onClick = {
                onCollectClick(articleItem)
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(32.dp),
        ) {
            Icon(
                imageVector = if (articleItem.collect) Icons.Filled.Favorite else Icons.Outlined.Favorite,
                contentDescription = null,
                tint = if (articleItem.collect) Color.Red else Color.LightGray
            )
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun ArticleItemCardPreview() {
    ArticleItemView(
        articleItem = ArticleItem(
            id = 0,
            title = "你可能没有那么了解 RecycleView",
            shareUser = "Nicho",
            author = "Nicho",
            niceShareDate = "1天前",
            link = "https://www.baidu.com",
            chapterName = "Nicko学Android",
            superChapterName = "Compose",
        )
    )
}

@Composable
fun BannerComponent(
    bannerList: List<BannerItem>,
    modifier: Modifier = Modifier,
    onBannerClick: (BannerItem) -> Unit = {}
) {
    if (bannerList.isEmpty()) return
    val initialPage = Int.MAX_VALUE / 2 - (Int.MAX_VALUE / 2 % bannerList.size)
    val pagerState = rememberPagerState(initialPage, pageCount = { Int.MAX_VALUE })
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()
    if (!isDragged) {
        LaunchedEffect(Unit) {
            while (true) {
                delay(3000)
                pagerState.animateScrollToPage(pagerState.currentPage + 1)
            }
        }
    }
    val context = LocalContext.current
    Box(modifier = modifier.fillMaxWidth()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
        ) { page ->
            val banner = bannerList[page % bannerList.size]
            AsyncImage(
                model = banner.imagePath,
                contentDescription = banner.title,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
//                        onBannerClick(banner)
                        Log.d("Banner", "点击了: ${banner.url}")
                        launchCustomChrome(context, banner.url)
                    },
                contentScale = ContentScale.Crop
            )
        }
        BannerIndicator(
            count = bannerList.size,
            currentIndex = pagerState.currentPage % bannerList.size,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp)
        )
    }
}

@Composable
fun BannerIndicator(count: Int, currentIndex: Int, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        repeat(count) { iteration ->
            val color = if (currentIndex == iteration)
                androidx.compose.ui.graphics.Color.Red
            else
                androidx.compose.ui.graphics.Color.Red.copy(alpha = 0.5f)

            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(androidx.compose.foundation.shape.CircleShape)
                    .background(color)
                    .size(6.dp)
            )
        }
    }
}

@Composable
fun FootView(
    isLoading: Boolean,
    isEnd: Boolean,
    itemCount: Int
) {
    if (itemCount <= 0) return
    if (isLoading) {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(16.dp), contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(modifier = Modifier.size(24.dp))
        }
    } else if (isEnd) {
        Text(
            "没有更多数据了", modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), textAlign = TextAlign.Center
        )
    }
}
