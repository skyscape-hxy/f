package com.skyscape.demo.frame.ui.fragment.two;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;


import com.skyscape.demo.frame.R;
import com.skyscape.demo.frame.adapter.PicAdapter;
import com.skyscape.demo.frame.base.BaseFragment;

import com.skyscape.demo.frame.bean.imp.StatusLayoutCallback;
import com.skyscape.demo.frame.databinding.FragmentTwoBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TwoFragment extends BaseFragment<TwoVM, FragmentTwoBinding> {
    private List<String> pics=new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_two;
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
       getImage();
    }

    @Override
    protected void setListener() {


    }
    /**
     * 获取图像列表
     */

    void getImage() {
        String[] projection = { MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATA };
        String orderBy = MediaStore.Images.Media.DISPLAY_NAME;
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        getContentProvider(uri,projection, orderBy);
    }

    public void getContentProvider(Uri uri,String[] projection, String orderBy) {
        // TODO Auto-generated method stub

        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null,
                null, orderBy);
        if (null == cursor) {
            return;
        }
        while (cursor.moveToNext()) {
            int columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
            String columnName = cursor.getString(columnIndex);
            pics.add(columnName);
            Log.e("TAG", "getContentProvider:----------- "+columnName );
        }
        PicAdapter picAdapter = new PicAdapter(getContext(), pics);
        binding.rv.setAdapter(picAdapter);
        Log.e("TAG", "getContentProvider:完成-----" );

    }



}