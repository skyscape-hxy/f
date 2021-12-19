package com.skyscape.demo.frame.bean.imp;

import android.annotation.SuppressLint;
import android.view.View;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

/**
 * @author: Administrator
 * @date: 2021/6/10
 * @description
 */
public class ViewOnClickListener implements View.OnClickListener {
    private Action mAction;

    public ViewOnClickListener(Action action) {
        mAction = action;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onClick(View v) {
        RxView.clicks(v)
                .throttleFirst(1, TimeUnit.SECONDS)//1秒钟内只允许点击1次
                .subscribe(o -> {
                    if (mAction!=null) {
                        mAction.call();
                    }
                });
    }

}
