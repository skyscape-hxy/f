package com.skyscape.demo.frame.launchstater.mytasks;

import com.skyscape.demo.frame.launchstater.task.MainTask;

import com.tencent.mmkv.MMKV;

/**
 * @author: Administrator
 * @date: 2021/6/7
 * @description
 */
public class MMKVTask extends MainTask {

    @Override
    public void run() {
        MMKV.initialize(mContext);

    }
}