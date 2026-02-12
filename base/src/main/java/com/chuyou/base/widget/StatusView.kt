package com.chuyou.base.widget

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

/**
 * 一个用于在布局中占位状态栏高度的 View。
 * 解决了在 XML 中无需手动计算状态栏高度的问题。
 */
class StatusView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // 状态栏高度，只计算一次
    private val statusBarHeight: Int by lazy {
        getStatusBarHeight(context)
    }

    // 重写 onMeasure 方法来设置自身的高度
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // 设置 View 的高度为状态栏的高度
        val heightSpec = MeasureSpec.makeMeasureSpec(statusBarHeight, MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasureSpec, heightSpec)
    }

    /**
     * 获取状态栏高度的方法
     */
    private fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }

        // 如果无法获取（例如在某些定制 ROM 或老版本上），使用一个默认值，但推荐方法更准确
        if (result == 0) {
            // 默认值，例如 24dp (转换为像素)
            result = (24 * context.resources.displayMetrics.density).toInt()
        }
        return result
    }

    // 可选：重写 onDraw，确保 View 不会绘制任何内容
    override fun onDraw(canvas: Canvas) {
        // 保持为空，因为它只是一个透明的占位符
    }
}