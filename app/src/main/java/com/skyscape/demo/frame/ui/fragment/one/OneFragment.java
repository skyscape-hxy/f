package com.skyscape.demo.frame.ui.fragment.one;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;


import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.documentfile.provider.DocumentFile;

import com.skyscape.demo.frame.MediaFile;
import com.skyscape.demo.frame.R;
import com.skyscape.demo.frame.adapter.MyAdapter;
import com.skyscape.demo.frame.base.BaseFragment;
import com.skyscape.demo.frame.bean.basebean.ParamsBuilder;
import com.skyscape.demo.frame.bean.databean.HomeFatherBean;
import com.skyscape.demo.frame.databinding.FragmentOneBinding;
import com.skyscape.demo.frame.utils.DataUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.skyscape.demo.frame.bean.basebean.Resource.ERROR;
import static com.skyscape.demo.frame.bean.basebean.Resource.SUCCESS;


public class OneFragment extends BaseFragment<OneVM, FragmentOneBinding> {
    private List<DocumentFile> fs = new ArrayList<>();
    private MyAdapter myAdapter;
    private DocumentFile parentFile;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_one;
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
       requestPermission();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void setListener() {


    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    getFile();
                }
            }
        }
    }
    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
        } else {
            getFile();
        }
    }
    private void getFile() {
        Log.e("Tag", "getFile: 开始");

        File directory = Environment.getExternalStorageDirectory();
        DocumentFile root = DocumentFile.fromFile(directory);
        DocumentFile[] files = root.listFiles();
        if (files != null) {
            for (DocumentFile file : files) {
                if (file.isDirectory()|| MediaFile.isImageFileType(file.getName())||
                        MediaFile.isAudioFileType(file.getName())||
                        MediaFile.isVideoFileType(file.getName())){
                    Log.e("TAG", "getFile:附录家--》"+file.getParentFile().getName() );
                    fs.add(file);
                }


            }
        }
         myAdapter = new MyAdapter(getContext(), fs);
        binding.rvAllFile.setAdapter(myAdapter);
        myAdapter.setOnclickLinstener(new MyAdapter.OnclickLinstener() {
            @Override
            public void click(int p) {
                DocumentFile documentFiles = fs.get(p);
                parentFile=documentFiles.getParentFile();
                Log.e("TAG", "click: 点击"+parentFile.getName() );
                DocumentFile[] list = documentFiles.listFiles();
                List<DocumentFile>documentFileList=new ArrayList<>();
                for (DocumentFile file : list) {
                    if (file.isDirectory()||MediaFile.isImageFileType(file.getName())||
                            MediaFile.isAudioFileType(file.getName())||
                            MediaFile.isVideoFileType(file.getName())){
                        documentFileList.add(file);
                    }

                }
                myAdapter.refreshData(documentFileList);
            }
        });
        Log.e("TAGy", "getFile: 完成 ");
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
////        DocumentFile[] list = parentFile.listFiles();
////        List<DocumentFile>documentFileList=new ArrayList<>();
////        for (DocumentFile file : list) {
////            if (file.isDirectory()||MediaFile.isImageFileType(file.getName())||
////                    MediaFile.isAudioFileType(file.getName())||
////                    MediaFile.isVideoFileType(file.getName())){
////                documentFileList.add(file);
////            }
////
////        }
////        Log.e("TAG", "onBackPressed: 返回"+myAdapter);
////        myAdapter.refreshData(documentFileList);
//    }




}