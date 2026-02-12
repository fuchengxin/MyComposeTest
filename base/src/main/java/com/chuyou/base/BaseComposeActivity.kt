package com.chuyou.base

import android.view.View
import androidx.compose.runtime.Composable
import com.chuyou.base.widget.MyToolBarLayout

abstract class BaseComposeActivity : BaseActivity() {

    /**
     * 标题栏
     */
    @Composable
    fun ToolBarLayout(
        title: String? = null,
        titleId: Int? = null,
        rightTitle: String? = null,
        rightTitleId: Int? = null,
        rightTitleColorId: Int? = null,
        titleColorId: Int? = R.color.white,
        layoutColor: String? = null,
        layoutColorId: Int? = null,
        backIcon: Int? = R.drawable.btn_nav_back,
        rightIcon: Int? = null,
        lineGone: Boolean = true,
        backGone: Boolean = false,
        backgroundAlpha: Int? = 0,
        rightVisibility: Int? = null,
        rightListener: View.OnClickListener? = null,
        rightIconListener: View.OnClickListener? = null,
    ) {
        MyToolBarLayout(
            title = title,
            titleId = titleId,
            rightTitle = rightTitle,
            rightTitleId = rightTitleId,
            rightTitleColorId = rightTitleColorId,
            titleColorId = titleColorId,
            layoutColor = layoutColor,
            layoutColorId = layoutColorId,
            backIcon = backIcon,
            rightIcon = rightIcon,
            lineGone = lineGone,
            backGone = backGone,
            backgroundAlpha = backgroundAlpha,
            rightVisibility = rightVisibility,
            rightListener = rightListener,
            rightIconListener = rightIconListener,
            backListener = {
                if (!onBackClick()) {
                    finish()
                }
            })
    }


}