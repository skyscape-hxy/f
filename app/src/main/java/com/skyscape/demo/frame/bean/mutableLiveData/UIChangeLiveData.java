package com.skyscape.demo.frame.bean.mutableLiveData;

import java.util.Map;

/**
 * @author: Administrator
 * @date: 2021/6/11
 * @description
 */
public final class UIChangeLiveData extends SingleLiveEvent{
    private SingleLiveEvent<Map<String,Object>> startActivityEvent;
    private SingleLiveEvent<Void> finishEvent;


    public SingleLiveEvent<Map<String, Object>> getStartActivityEvent() {
        return startActivityEvent;
    }

    public void setStartActivityEvent(SingleLiveEvent<Map<String, Object>> startActivityEvent) {
        this.startActivityEvent = startActivityEvent;
    }

    public SingleLiveEvent<Void> getFinishEvent() {
        return finishEvent;
    }

    public void setFinishEvent(SingleLiveEvent<Void> finishEvent) {
        this.finishEvent = finishEvent;
    }


}