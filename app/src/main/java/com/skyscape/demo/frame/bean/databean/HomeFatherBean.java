package com.skyscape.demo.frame.bean.databean;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo
 * on 2019/11/11.
 */
public class HomeFatherBean implements Serializable {
    private List<HomeBean> datas;

    public List<HomeBean> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<HomeBean> datas) {
        this.datas = datas;
    }
}
