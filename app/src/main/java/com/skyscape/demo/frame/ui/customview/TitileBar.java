package com.skyscape.demo.frame.ui.customview;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.skyscape.demo.frame.R;

/**
 * @author: Administrator
 * @date: 2021/5/27
 * @description
 */
public class TitileBar extends FrameLayout {
    public LinearLayout titleBar;
    public TextView txt_title;
    public ImageView bar_left_btn;
    public TextView bar_right_text;
    public ImageView bar_right_btn;
    //这个用来添加子view
    public RelativeLayout view_container;
    public TextView line;
    public LinearLayout linear_;
    private int mBackColor;
    private int mTitleColor;
    private int mTitleSize;
    private String mTitleStr;
    private boolean mIsShowLeftBtn;
    private Drawable mLeftDrawable;
    private String mRightStr;
    private Drawable mRightDrawable;
    private int mDivide_color;

    public TitileBar(@NonNull Context context) {
        this(context, null);
    }

    public TitileBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitileBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        fbiView(context);
        init(attrs);
        initListener(context);
    }

    private void initListener(Context context) {
        bar_left_btn.setOnClickListener(v -> ((Activity)context).finish());
    }

    private void fbiView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.layout_toolbar, this);
        titleBar = findViewById(R.id.title_Bar);
        txt_title = findViewById(R.id.txt_title);
        bar_left_btn = findViewById(R.id.bar_left_btn);
        bar_right_text = findViewById(R.id.bar_right_text);
        bar_right_btn = findViewById(R.id.bar_right_btn);
        view_container = findViewById(R.id.view_container);
        linear_ = findViewById(R.id.linear_);
        line = findViewById(R.id.line);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 1) {
            View view = getChildAt(1);
            removeViewInLayout(view);
            if (view != null) {
                view_container.addView(view);
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (!TextUtils.isEmpty(txt_title.getText().toString().trim())) {
            confirmTitle();
        }
    }

    public void setTitle(String s) {
        txt_title.setText(s);
        confirmTitle();
    }

    public void confirmTitle() {
        int leftDistance = 0;//左边的距离
        if (bar_left_btn.getVisibility() == View.VISIBLE) {
            leftDistance = (int) getResources().getDimension(R.dimen.dp_35);
        }

        int rightDistance = 0;//右边的距离
        if (bar_right_btn.getVisibility() == View.GONE && bar_right_text.getVisibility() == View.GONE) {

        } else {
            rightDistance = linear_.getMeasuredWidth();
        }

        if (view_container.getChildCount() > 0) {
            rightDistance += view_container.getMeasuredWidth();
        }
        int max = Math.max(leftDistance, rightDistance);

        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int screenWith = width;
        int textWith = (int) txt_title.getPaint().measureText(txt_title.getText().toString().trim());
        if (textWith == 0) {
            return;
        }

        if ((screenWith - 2 * max) > textWith) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) txt_title.getLayoutParams();
            layoutParams.leftMargin = max;
            layoutParams.rightMargin = max;
            txt_title.setLayoutParams(layoutParams);
        } else {
            if (rightDistance == 0) {
                rightDistance = (int) getResources().getDimension(R.dimen.dp_35);
            }

            if (leftDistance == 0) {
                leftDistance = (int) getResources().getDimension(R.dimen.dp_35);
            }
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) txt_title.getLayoutParams();
            layoutParams.leftMargin = leftDistance;
            layoutParams.rightMargin = rightDistance;
            txt_title.setLayoutParams(layoutParams);
        }
    }

    private void init(AttributeSet attrs) {
        //自定义属性，
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TitleBar);

        //背景颜色
        mBackColor = typedArray.getColor(R.styleable.TitleBar_hl_background, Color.WHITE);
        setBackColor(mBackColor);

        //toolbar标题
        mTitleColor = typedArray.getColor(R.styleable.TitleBar_hl_textTitleColor, Color.BLACK);
        setTitleColor(mTitleColor);
        mTitleSize = (int) typedArray.getDimension(R.styleable.TitleBar_hl_textTitleSize, 18);
        setTitleSize(mTitleSize);
        mTitleStr = typedArray.getString(R.styleable.TitleBar_hl_textTitle);
        setTitleStr(mTitleStr);

        //左边图标
        mIsShowLeftBtn = typedArray.getBoolean(R.styleable.TitleBar_hl_showLeftBtn, true);
        setShowLeftBtn(mIsShowLeftBtn);

        mLeftDrawable = typedArray.getDrawable(R.styleable.TitleBar_hl_leftBtnDrawable);
        setLeftDrawable(mLeftDrawable);

        /*
         * 右边
         * */

        //右边是否显示文字
        mRightStr = typedArray.getString(R.styleable.TitleBar_hl_showRightText);
        setRightStr(mRightStr);

        //右边是否显示图标
        mRightDrawable = typedArray.getDrawable(R.styleable.TitleBar_hl_rightBtnDrawable);
        setRightDrawable(mRightDrawable);

        //分割线颜色，如果bar背景颜色和window背景颜色一致，需要分割线
        mDivide_color = typedArray.getColor(R.styleable.TitleBar_hl_divideColor, Color.TRANSPARENT);
        setDivide_color(mDivide_color);
    }

    public void setBackColor(int backColor) {
        mBackColor = backColor;
        titleBar.setBackgroundColor(mBackColor);
    }

    public void setTitleColor(int titleColor) {
        mTitleColor = titleColor;
        txt_title.setTextColor(mTitleColor);
    }

    public void setTitleSize(int titleSize) {
        mTitleSize = titleSize;
        txt_title.setTextSize(mTitleSize);
    }

    public void setTitleStr(String titleStr) {
        mTitleStr = titleStr;
        if (TextUtils.isEmpty(mTitleStr)) {
            txt_title.setText("");
        } else {
            txt_title.setText(mTitleStr);
        }
    }

    public void setShowLeftBtn(boolean showLeftBtn) {
        mIsShowLeftBtn = showLeftBtn;
        if (mIsShowLeftBtn) {
            bar_left_btn.setVisibility(View.VISIBLE);
        } else {
            bar_left_btn.setVisibility(View.GONE);
        }
    }

    public void setLeftDrawable(Drawable leftDrawable) {
        mLeftDrawable = leftDrawable;
        if (mLeftDrawable != null) {
            bar_left_btn.setImageDrawable(mLeftDrawable);
        }
    }

    public void setRightStr(String rightStr) {
        mRightStr = rightStr;
        if (TextUtils.isEmpty(mRightStr)) {
            bar_right_text.setVisibility(View.GONE);
        } else {
            bar_right_text.setText(mRightStr);
        }
    }

    public void setRightDrawable(Drawable rightDrawable) {
        mRightDrawable = rightDrawable;
        if (mRightDrawable == null) {
            bar_right_btn.setVisibility(View.GONE);
        } else {
            bar_right_btn.setVisibility(View.VISIBLE);
            bar_right_btn.setImageDrawable(mRightDrawable);
        }
    }

    public void setDivide_color(int divide_color) {
        mDivide_color = divide_color;
        line.setBackgroundColor(mDivide_color);
    }
}