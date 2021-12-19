package com.skyscape.demo.frame.base;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.skyscape.demo.frame.bean.basebean.ParamsBuilder;
import com.skyscape.demo.frame.bean.basebean.Resource;
import com.skyscape.demo.frame.bean.basebean.ResponModel;
import com.skyscape.demo.frame.http.ApiService;
import com.skyscape.demo.frame.http.RetrofitManager;
import com.skyscape.demo.frame.http.interceptor.NetCacheInterceptor;
import com.skyscape.demo.frame.http.interceptor.OfflineCacheInterceptor;
import com.trello.rxlifecycle2.LifecycleTransformer;


import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.schedulers.IoScheduler;
import io.reactivex.schedulers.Schedulers;


/**
 * @author: Administrator
 * @date: 2021/5/24
 * @description
 */
public class BaseModel {
    public LifecycleTransformer objectLifecycleTransformer;
    public ArrayList<String> onNetTags;

    public ApiService getApiService() {
        return RetrofitManager.getRetrofitManager().getApiService();
    }
    public void setObjectLifecycleTransformer(LifecycleTransformer objectLifecycleTransformer) {
        this.objectLifecycleTransformer = objectLifecycleTransformer;
    }

    public void setOnNetTags(ArrayList<String> onNetTags) {
        this.onNetTags = onNetTags;
    }


    public <T> MutableLiveData<T> observeGo(Observable observable, final MutableLiveData<T> liveData) {
        return observe(observable, liveData, null);
    }

    public <T> MutableLiveData<T> observeGo(Observable observable,
                                            final MutableLiveData<T> liveData,
                                            ParamsBuilder paramsBuilder) {
        if (paramsBuilder == null) {
            paramsBuilder = ParamsBuilder.build();
        }
        int retryCount = paramsBuilder.getRetryCount();
        if (retryCount > 0) {
            return observeWithRetry(observable, liveData, paramsBuilder);
        } else {
            return observe(observable, liveData, paramsBuilder);
        }
    }

    //把统一操作全部放在这，不会重连
    private <T> MutableLiveData<T> observe(Observable observable,
                                           MutableLiveData<T> liveData,
                                           ParamsBuilder paramsBuilder) {
        if (paramsBuilder == null) {
            paramsBuilder = ParamsBuilder.build();
        }
        boolean showDialog = paramsBuilder.isShowDialog();
        String loadingMessage = paramsBuilder.getLoadingMessage();
        int onlineCacheTime = paramsBuilder.getOnlineCacheTime();
        int offlineCacheTime = paramsBuilder.getOfflineCacheTime();

        if (onlineCacheTime > 0) {
            setOnlineCacheTime(onlineCacheTime);
        }
        if (offlineCacheTime > 0) {
            setOfflineCacheTime(offlineCacheTime);
        }
        String oneTag = paramsBuilder.getOneTag();
        if (!TextUtils.isEmpty(oneTag)) {
            if (onNetTags.contains(oneTag)) {
                return liveData;
            }
        }
        observable.subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        if (!TextUtils.isEmpty(oneTag)) {
                            onNetTags.add(oneTag);
                        }
                        if (showDialog) {
                            liveData.postValue((T) Resource.loading(loadingMessage));
                        }
                    }
                })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        if (!TextUtils.isEmpty(oneTag)) {
                            onNetTags.remove(oneTag);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                    liveData.postValue((T) Resource.response((ResponModel<Object>) o));
                }, throwable -> {
                    liveData.postValue((T) Resource.error((Throwable) throwable));
                });
        return liveData ;
    }

    //把统一操作全部放在这，这是带重连的
    private <T> MutableLiveData<T> observeWithRetry(Observable observable,
                                                    MutableLiveData<T> liveData,
                                                    ParamsBuilder paramsBuilder) {
        if (paramsBuilder == null) {
            paramsBuilder = paramsBuilder.build();
        }
        boolean showDialog = paramsBuilder.isShowDialog();
        String loadingMessage = paramsBuilder.getLoadingMessage();
        int onlineCacheTime = paramsBuilder.getOnlineCacheTime();
        int offlineCacheTime = paramsBuilder.getOfflineCacheTime();

        if (onlineCacheTime > 0) {
            setOnlineCacheTime(onlineCacheTime);
        }
        if (offlineCacheTime > 0) {
            setOfflineCacheTime(offlineCacheTime);
        }

        String oneTag = paramsBuilder.getOneTag();
        if (!TextUtils.isEmpty(oneTag)) {
            if (onNetTags.contains(oneTag)) {
                return liveData;
            }
        }
        final int maxCount = paramsBuilder.getRetryCount();
        final int[] currentCount = {0};

        Disposable disposable = observable.subscribeOn(Schedulers.io())
                .retryWhen(throwable -> {
                    //如果还没到次数，就延迟5秒发起重连
                    if (currentCount[0] <= maxCount) {
                        currentCount[0]++;
                        return Observable.just(1).delay(5000, TimeUnit.MILLISECONDS);
                    } else {
                        //到次数了跑出异常
                        return Observable.error(new Throwable("重连次数已达最高,请求超时"));
                    }
                })
                .doOnSubscribe(disposable1 -> {
                    if (!TextUtils.isEmpty(oneTag)) {
                        onNetTags.add(oneTag);
                    }
                    if (showDialog) {
                        liveData.postValue((T) Resource.loading(loadingMessage));
                    }
                })
                .doFinally(() -> {
                    if (!TextUtils.isEmpty(oneTag)) {
                        onNetTags.remove(oneTag);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                //.compose(objectLifecycleTransformer)
                .subscribe(o -> {
                    liveData.postValue((T) Resource.response((ResponModel<Object>) o));
                }, throwable -> {
                    liveData.postValue((T) Resource.error((Throwable) throwable));
                });

        return liveData;
    }

    //设置在线网络缓存
    public void setOnlineCacheTime(int time) {
        NetCacheInterceptor.getInstance().setOnlineTime(time);
    }

    //设置离线网络缓存
    public void setOfflineCacheTime(int time) {
        OfflineCacheInterceptor.getInstance().setOfflineCacheTime(time);
    }
}