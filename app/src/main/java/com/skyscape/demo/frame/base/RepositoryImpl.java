package com.skyscape.demo.frame.base;


import androidx.lifecycle.MutableLiveData;

import com.skyscape.demo.frame.bean.basebean.ParamsBuilder;
import com.skyscape.demo.frame.bean.basebean.Resource;
import com.skyscape.demo.frame.bean.databean.HomeFatherBean;

import java.util.List;
import java.util.Map;

/**
 * Created by leo
 * on 2019/10/15.
 * 这是所有的网络请求所在的位置
 */
public class RepositoryImpl extends BaseModel {
    //获取首页文章
    public MutableLiveData<Resource<HomeFatherBean>> getHomeArticles(int curPage, ParamsBuilder paramsBuilder) {

        MutableLiveData<Resource<HomeFatherBean>> liveData = new MutableLiveData<>();
        return observeGo(getApiService().getHomeArticles(curPage), liveData, paramsBuilder);
    }




}
