package com.skyscape.demo.frame.ui.fragment.two;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.skyscape.demo.frame.base.BaseViewModel;
import com.skyscape.demo.frame.base.RepositoryImpl;
import com.skyscape.demo.frame.bean.basebean.ParamsBuilder;
import com.skyscape.demo.frame.bean.basebean.Resource;
import com.skyscape.demo.frame.bean.databean.HomeFatherBean;

import org.jetbrains.annotations.NotNull;

/**
 * @author: Administrator
 * @date: 2021/6/30
 * @description
 */
public class TwoVM extends BaseViewModel<RepositoryImpl> {
    public TwoVM(@NonNull @NotNull Application application) {
        super(application);
    }

    //获取首页文章
    public LiveData<Resource<HomeFatherBean>> getHomeArticles(int curPage, ParamsBuilder paramsBuilder) {
        return getRepository().getHomeArticles(curPage, paramsBuilder);
    }
}