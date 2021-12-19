package com.skyscape.demo.frame.http;

import android.os.Environment;

import com.skyscape.demo.frame.BuildConfig;
import com.skyscape.demo.frame.common.SystemConst;
import com.skyscape.demo.frame.http.interceptor.HttpLogInterceptor;
import com.skyscape.demo.frame.http.interceptor.NetCacheInterceptor;
import com.skyscape.demo.frame.http.interceptor.OfflineCacheInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author: Administrator
 * @date: 2021/5/21
 * @description
 */
public class RetrofitManager {
    private static final long CACHE_SIZE=50 * 1024 * 1024;
    private static int DEFAULT_TIMEOUT=20;
    private static RetrofitManager retrofitManager;
    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    private ApiService apiService;


    public RetrofitManager() {
        initOkHttpClient();
        initRetrofit();
    }

    public static RetrofitManager getRetrofitManager() {
        if (retrofitManager==null) {
            synchronized (RetrofitManager.class){
                if (retrofitManager==null) {
                    retrofitManager=new RetrofitManager();
                }
            }
        }
        return retrofitManager;
    }

    public static ApiService getApiService() {
        if (retrofitManager==null) {
            retrofitManager=getRetrofitManager();
        }
        return retrofitManager.apiService;
    }

    private void initOkHttpClient() {
        okHttpClient = new OkHttpClient.Builder()
                //设置缓存文件路径，和文件大小
                .cache(new Cache(new File(Environment.getExternalStorageDirectory() + "/okhttp_cache/"), CACHE_SIZE))
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS)
                .addInterceptor(new HttpLogInterceptor())
                //设置在线和离线缓存
                .addInterceptor(OfflineCacheInterceptor.getInstance())
                .addNetworkInterceptor(NetCacheInterceptor.getInstance())
                .build();
    }
    private void initRetrofit(){
        String baseUrl= SystemConst.DEFAULT_SERVER_DEBUG;
        if (BuildConfig.BUILD_TYPE.equals("debug")){
            baseUrl=SystemConst.DEFAULT_SERVER_DEBUG;
        }else {
            baseUrl=SystemConst.DEFAULT_SERVER_RELEASE;
        }
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

    }
}