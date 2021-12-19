package com.skyscape.demo.frame.base;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.skyscape.demo.frame.bean.mutableLiveData.SingleLiveEvent;
import com.trello.rxlifecycle2.LifecycleTransformer;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Administrator
 * @date: 2021/5/20
 * @description
 */
public class BaseViewModel<T extends BaseModel> extends AndroidViewModel {
    private UIChangeLiveData uc;
    private T repository;
    private ArrayList<String> onNetTags;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        createRepository();
        onNetTags = new ArrayList<>();
        repository.setOnNetTags(onNetTags);
    }

    public void setObjectLifecycleTransformer(LifecycleTransformer objectLifecycleTransformer) {
        if (repository != null) {
            repository.setObjectLifecycleTransformer(objectLifecycleTransformer);
        }
    }

    private void createRepository() {
        if (repository == null) {
            repository = (T) new RepositoryImpl();
        }
    }

    public T getRepository() {
        return repository;
    }

    public UIChangeLiveData getUc() {
        if (uc == null) {
            uc = new UIChangeLiveData();
        }
        return uc;
    }



    @Override
    protected void onCleared() {
        super.onCleared();
    }
    /****************************************************************/
    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Map<String, Object> params = new HashMap<>();
        params.put(ParameterField.CLASS, clz);
        if (bundle != null) {
            params.put(ParameterField.BUNDLE, bundle);
        }
        uc.startActivityEvent.setValue((params));
    }

    /**
     * 关闭界面
     */
    public void finish() {
        uc.finishEvent.call();
    }

    public static final class ParameterField {
        public static String CLASS = "CLASS";
        public static String BUNDLE = "BUNDLE";
    }

    public final class UIChangeLiveData extends SingleLiveEvent {
        private SingleLiveEvent<Map<String, Object>> startActivityEvent;
        private SingleLiveEvent<Void> finishEvent;

        public SingleLiveEvent<Map<String, Object>> getStartActivityEvent() {
            return startActivityEvent = createLiveData(startActivityEvent);
        }

        public SingleLiveEvent<Void> getFinishEvent() {
            return finishEvent = createLiveData(finishEvent);
        }

        private <T> SingleLiveEvent<T> createLiveData(SingleLiveEvent<T> liveData) {
            if (liveData == null) {
                liveData = new SingleLiveEvent<>();
            }
            return liveData;
        }

        @Override
        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer observer) {
            super.observe(owner, observer);
        }
    }

}