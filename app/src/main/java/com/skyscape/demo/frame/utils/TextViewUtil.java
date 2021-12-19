package com.skyscape.demo.frame.utils;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author: Administrator
 * @date: 2021/5/24
 * @description
 */
public class TextViewUtil {
    //快速获取textView 或 EditText上文字内容
    public static String getStringByUI(View view) {
        if (view instanceof EditText) {
            return ((EditText) view).getText().toString().trim();
        } else if (view instanceof TextView) {
            return ((TextView) view).getText().toString().trim();
        }
        return "";
    }
}