package com.chuyou.base.widget

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView


/**
 * 封装公共标题栏
 */
@Preview
@Composable
private fun ToolBarLayoutPreview() {
    MyToolBarLayout(
        title = "标题",
        rightTitle = "标题",
    )
}

@Composable
fun MyToolBarLayout(
    title: String? = null,
    titleId: Int? = null,
    rightTitle: String? = null,
    rightTitleId: Int? = null,
    rightTitleColorId: Int? = null,
    titleColorId: Int? = null,
    layoutColor: String? = null,
    layoutColorId: Int? = null,
    backIcon: Int? = null,
    rightIcon: Int? = null,
    lineGone: Boolean = false,
    backGone: Boolean = false,
    backgroundAlpha: Int? = null,
    rightVisibility: Int? = null,
    backListener: View.OnClickListener? = null,
    rightListener: View.OnClickListener? = null,
    rightIconListener: View.OnClickListener? = null,
) {
    val toolBarLayout = remember {
        { context: Context ->
            ToolBarLayout(context).apply {
                orientation = LinearLayout.VERTICAL
            }
        }
    }
    AndroidView(
        factory = toolBarLayout, update = { view ->
            view.apply {
                this.setTitle(title)
                titleId?.let { setTitle(it) }
                setRightTitle(rightTitle)
                rightTitleId?.let { setRightTitle(it) }
                rightTitleColorId?.let { setRightTitleColor(it) }
                backListener?.let { addBackListener(backListener) }
                rightListener?.let { setRightListener(it) }
                rightIconListener?.let { setRightIconListener(it) }
                backgroundAlpha?.let { setBackgroundAlpha(it) }
                titleColorId?.let { setTitleColor(it) }
                layoutColorId?.let { setLayoutColor(it) }
                layoutColor?.let { setLayoutColor(layoutColor) }
                backIcon?.let { setBackIcon(it) }
                rightIcon?.let { setRightIcon(it) }
                rightVisibility?.let { setEditVisible(it) }
                if (lineGone) setLineGone()
                if (backGone) setBackGone()
            }
        }, modifier = Modifier
            .fillMaxWidth()
    )
}