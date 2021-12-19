package com.skyscape.demo.frame.utils;

import android.content.Context;
import android.widget.Toast;




/**
 * Created by leo on 2017/9/4.
 * 防止重复点击toast，一直显示未隐藏
 */

public class ToastUtils {
    private static Context context = ContextUtils.getApplication();
    /**
     * Toast对象
     */
    private static Toast toast = null;

    /**
     * 显示Toast
     *
     * @param message
     */
    public static void showToast(String message) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
