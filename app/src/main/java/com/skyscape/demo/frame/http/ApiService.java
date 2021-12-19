package com.skyscape.demo.frame.http;


import com.skyscape.demo.frame.bean.basebean.ResponModel;
import com.skyscape.demo.frame.bean.databean.HomeFatherBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by leo
 * on 2019/8/14.
 * Retrofit 接口请求配置都在这
 */
public interface ApiService {
    //首页文章,curPage拼接。从0开始
    @GET("article/list/{curPage}/json")
    Observable<ResponModel<HomeFatherBean>> getHomeArticles(@Path("curPage") int curPage);

}
