package com.skyscape.demo.frame.utils;

import android.view.View;


import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.skyscape.demo.frame.bean.databean.HomeBean;
import com.skyscape.demo.frame.databinding.IncludeStatusBinding;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.skyscape.demo.frame.bean.basebean.Resource.SUCCESS;

/**
 * Created by leo
 * on 2020/10/19.
 * DataUtils 填充数据工具类
 */
public class DataUtils {

    private DataUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }


    public static <T extends Object> void initData(int currenPage,List<?> arrayList,
                                                   ObservableList<T> dataList,
                                                   SmartRefreshLayout smartRefreshLayout,
                                                   View contentView,
                                                   IncludeStatusBinding binding,int status) {
        if (currenPage == 0) {
            dataList.clear();
        }
        dataList.addAll((Collection<? extends T>) arrayList);
        initData( arrayList,  smartRefreshLayout);
        if (binding != null) {
            isShowView(currenPage,(ArrayList<?>) dataList,contentView, binding ,status);
        }
    }

    public static <T extends Object> void initData( List<?> arrayList, SmartRefreshLayout smartRefreshLayout) {
        if (arrayList == null || arrayList.size() == 0) {
            if (smartRefreshLayout != null) {
                smartRefreshLayout.finishLoadMoreWithNoMoreData();
            }
        }
    }


    public static void isShowView(int currenPage, ArrayList<?> arrayList, View contentView, IncludeStatusBinding binding, int status) {
        if (currenPage!=0){
            return;
        }
        if (status==SUCCESS) {
            contentView.setVisibility(View.VISIBLE);
            binding.rlError.setVisibility(View.GONE);
            if (arrayList != null && arrayList.size() > 0) {
                binding.rlEmpty.setVisibility(View.GONE);
            } else {
                binding.rlEmpty.setVisibility(View.VISIBLE);
            }
        }else {
            contentView.setVisibility(View.GONE);
            binding.rlError.setVisibility(View.VISIBLE);
        }
    }

}
