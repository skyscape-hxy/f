package com.skyscape.demo.frame;

import android.app.Application;
import android.content.Context;

import com.skyscape.demo.frame.launchstater.TaskDispatcher;
import com.skyscape.demo.frame.launchstater.mytasks.MMKVTask;
import com.skyscape.demo.frame.launchstater.mytasks.SmartRefreshLayoutTask;
import com.skyscape.demo.frame.utils.ContextUtils;


/**
 * Created by leo
 * on 2019/10/15.
 */
public class MyApplication extends Application {

    private static MyApplication context;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        ContextUtils.initContext(this);
        //bugly 设置
        //CrashReport.initCrashReport(getApplicationContext(), "注册时申请的APPID", false);
        //leakcanary
        //Image-Loader
        //EventBus
        //zxing
        TaskDispatcher.init(this);
        TaskDispatcher dispatcher = TaskDispatcher.createInstance();
        dispatcher.addTask(new SmartRefreshLayoutTask())
                .addTask(new MMKVTask())
                .start();
    }

    public static Context getContext() {
        return context;
    }
}
