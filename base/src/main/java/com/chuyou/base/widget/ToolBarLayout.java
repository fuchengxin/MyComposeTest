package com.chuyou.base.widget;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.annotation.StyleRes;
import androidx.core.content.ContextCompat;

import com.chuyou.base.R;


/**
 * 封装公共标题栏
 *
 * @author wangfei
 * @date 2019/11/26
 */
public class ToolBarLayout extends LinearLayout {

    public ToolBarLayout(Context context) {
        this(context, null);
    }

    public ToolBarLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ToolBarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 返回键
     */
    private ImageButton back;
    /**
     * 右图标
     */
    private ImageButton right;
    /**
     * 标题
     */
    private TextView title;
    /**
     * 右边按钮
     */
    private Button save;
    /**
     * 下划线
     */
    private View line;
    private View layout;


    private void init(Context context) {
        layout = LayoutInflater.from(context).inflate(R.layout.widget_include_toolbar_common, this, true);
        back = findViewById(R.id.ib_back);
        title = findViewById(R.id.tv_title);
        save = findViewById(R.id.btn_edit);
        right = findViewById(R.id.iv_right);
        line = findViewById(R.id.line);
        setBackgroundColor(ContextCompat.getColor(context, R.color.color_FF141414));
    }

    /**
     * 返回键与标题
     *
     * @param text
     * @param onClickListener
     */
    public void initData(String text, OnClickListener onClickListener) {
        title.setText(text);
        back.setOnClickListener(onClickListener);
    }

    /**
     * 设置右边按钮可见
     *
     * @param type
     */
    public void setEditVisible(int type) {
        save.setVisibility(type);
    }


    /**
     * 修改右侧菜单的文字内容
     */
    public ToolBarLayout updateEdit(String content) {
        save.setText(content);
        return this;
    }


    /**
     * 返回键
     */
    public ToolBarLayout addBackListener(OnClickListener onClickListener) {
        back.setOnClickListener(onClickListener);
        return this;
    }


    /**
     * 标题
     */
    public ToolBarLayout setTitle(String text) {
        title.setText(text);
        return this;
    }

    /**
     * 标题
     */
    public ToolBarLayout setTitle(int resId) {
        title.setText(getContext().getString(resId));
        return this;
    }

    /**
     * 设置标题颜色
     */
    public ToolBarLayout setTitleColor(int color) {
        title.setTextColor(ContextCompat.getColor(getContext(), color));
        return this;
    }


    /**
     * 设置右边按钮文字
     */
    public ToolBarLayout setRightTitle(int resId) {
        save.setText(getContext().getString(resId));
        return this;
    }

    /**
     * 设置右边按钮文字
     */
    public ToolBarLayout setRightTitle(String text) {
        save.setText(text);
        return this;
    }

    /**
     * 获取右边按钮文字
     */
    public String getRightTitle() {
        return save.getText().toString();
    }

    /**
     * 获取右边按钮
     */
    public Button getRightTitleButton() {
        return save;
    }

    /**
     * 设置右边按钮文字颜色
     */
    public ToolBarLayout setRightTitleColor(int color) {
        save.setTextColor(ContextCompat.getColor(getContext(), color));
        return this;
    }

    /**
     * 获取标题控件
     */
    public TextView getTitle() {
        return title;
    }

    /**
     * 设置左边颜色
     *
     * @param resId
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public ToolBarLayout setRightTitleColorStyle(@StyleRes int resId) {
        save.setTextAppearance(resId);
        return this;
    }

    public ToolBarLayout setRightIcon(int resourceId) {
        right.setImageResource(resourceId);
        right.setVisibility(VISIBLE);
        return this;
    }

    /**
     * 获取右边图标
     */
    public ImageButton getRightIcon() {
        return right;
    }

    /**
     * 设置右边点击事件
     */
    public ToolBarLayout setRightIconListener(OnClickListener onClickListener) {
        right.setVisibility(VISIBLE);
        right.setOnClickListener(v -> {
            right.setEnabled(false);
            if (onClickListener != null) {
                onClickListener.onClick(v);
            }
            new Handler().postDelayed(() -> right.setEnabled(true), 1000);
        });
        return this;
    }

    /**
     * 设置右边点击事件
     */
    public ToolBarLayout setRightListener(OnClickListener onClickListener) {
        save.setVisibility(VISIBLE);
        save.setOnClickListener(v -> {
            if (onClickListener != null) {
                onClickListener.onClick(v);
            }
        });
        return this;
    }


    /**
     * 设置背景颜色
     */
    public ToolBarLayout setLayoutColor(int color) {
        setBackgroundColor(ContextCompat.getColor(getContext(), color));
        setLineGone();
        return this;
    }

    /**
     * 设置背景颜色
     */
    public ToolBarLayout setLayoutColor(String color) {
        setBackgroundColor(Color.parseColor(color));
        setLineGone();
        return this;
    }


    /**
     * 设置返回图标
     */
    public ToolBarLayout setBackIcon(int resourceId) {
        back.setImageResource(resourceId);
        return this;
    }

    /**
     * 设置返回图标
     */
    public ToolBarLayout setLineGone() {
        return setLineGone(false);
    }

    public ToolBarLayout setLineGone(boolean isVisibility) {
        line.setVisibility(isVisibility ? VISIBLE : GONE);
        return this;
    }

    /**
     * 隐藏返回图标
     */
    public ToolBarLayout setBackGone() {
        back.setVisibility(GONE);
        return this;
    }

    /**
     * 背景透明
     */
    public ToolBarLayout setBackgroundAlpha(int alpha) {
        layout.getBackground().setAlpha(alpha);
        return this;
    }
}
