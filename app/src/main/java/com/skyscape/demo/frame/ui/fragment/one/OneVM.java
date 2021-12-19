package com.skyscape.demo.frame.ui.fragment.one;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import androidx.lifecycle.LiveData;

import com.skyscape.demo.frame.BR;
import com.skyscape.demo.frame.R;
import com.skyscape.demo.frame.base.BaseAdapter;
import com.skyscape.demo.frame.base.BaseViewModel;
import com.skyscape.demo.frame.base.OnItemClickListener;
import com.skyscape.demo.frame.base.RepositoryImpl;
import com.skyscape.demo.frame.bean.basebean.ParamsBuilder;
import com.skyscape.demo.frame.bean.basebean.Resource;
import com.skyscape.demo.frame.bean.databean.HomeBean;
import com.skyscape.demo.frame.bean.databean.HomeFatherBean;

import com.skyscape.demo.frame.ui.activity.WebActivity;


import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * @author: Administrator
 * @date: 2021/5/25
 * @description
 */
public class OneVM extends BaseViewModel<RepositoryImpl> implements OnItemClickListener<HomeBean> {
    public OneVM(@NonNull Application application) {
        super(application);
    }
    public BaseAdapter<HomeBean> mBaseAdapter=new BaseAdapter();



    //获取首页文章
    public LiveData<Resource<HomeFatherBean>> getHomeArticles(int curPage, ParamsBuilder paramsBuilder) {
        return getRepository().getHomeArticles(curPage, paramsBuilder);
    }

    public final ObservableList<HomeBean> items = new ObservableArrayList<>();
    public final ItemBinding<Object> itemBinding = ItemBinding.of(BR.homeBean, R.layout.item_home)
            .bindExtra(BR.listener, this);


    @Override
    public void onItemClick(HomeBean item) {
        Bundle bundle = new Bundle();
        bundle.putString("url", item.getLink());
        startActivity(WebActivity.class, bundle);
    }


}