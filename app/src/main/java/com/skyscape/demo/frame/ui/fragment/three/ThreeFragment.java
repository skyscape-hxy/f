package com.skyscape.demo.frame.ui.fragment.three;


import android.database.Cursor;
import android.graphics.drawable.ClipDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.skyscape.demo.frame.R;
import com.skyscape.demo.frame.adapter.PicAdapter;
import com.skyscape.demo.frame.base.BaseFragment;
import com.skyscape.demo.frame.base.NormalViewModel;
import com.skyscape.demo.frame.bean.imp.StatusLayoutCallback;
import com.skyscape.demo.frame.databinding.FragmentThreeBinding;

import java.util.ArrayList;
import java.util.List;


public class ThreeFragment extends BaseFragment<NormalViewModel, FragmentThreeBinding> {
private List<String> videos=new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_three;
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
getVideo();
    }

    @Override
    protected void setListener() {


    }
    /**
     * 获取视频列表
     */
    void getVideo() {
        // TODO Auto-generated method stub
        String []projection = { MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DATA};
        String orderBy = MediaStore.Video.Media.DISPLAY_NAME;
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
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
            videos.add(columnName);
            Log.e("TAG", "getContentProvider:----------- "+columnName );
        }
        PicAdapter picAdapter = new PicAdapter(getContext(), videos);
        binding.rv.setAdapter(picAdapter);
        Log.e("TAG", "getContentProvider:完成-----" );

    }





}