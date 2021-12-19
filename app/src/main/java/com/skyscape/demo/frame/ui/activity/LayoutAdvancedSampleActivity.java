package com.skyscape.demo.frame.ui.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.skyscape.demo.frame.R;
import com.skyscape.demo.frame.base.BaseActivity;
import com.skyscape.demo.frame.base.NormalViewModel;
import com.skyscape.demo.frame.databinding.ActivityLayoutSampleBinding;
import com.skyscape.demo.frame.databinding.ActivityMainBinding;

import me.samlss.broccoli.Broccoli;



public class LayoutAdvancedSampleActivity extends BaseActivity<NormalViewModel, ActivityLayoutSampleBinding> {


    private Broccoli mBroccoli;
    private Handler mHandler = new Handler();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_layout_sample;
    }

    @Override
    protected void processLogic() {

    }

    @Override
    protected void setListener() {

    }
//    private void initPlaceholders(){
//        mBroccoli = new Broccoli();
//        mBroccoli.addPlaceholders(this, R.id.tv_view_time, R.id.tv_collect_time,
//                R.id.tv_price, R.id.iv_clock, R.id.tv_time, R.id.iv_calendar, R.id.iv_location,
//                R.id.tv_location, R.id.iv_arrow_right, R.id.iv_logo, R.id.tv_organizer_name, R.id.tv_organizer_description,
//                R.id.tv_fans, R.id.tv_fans_number, R.id.tv_events, R.id.tv_events_number, R.id.tv_follow, R.id.tv_station);
//
//        showPlaceholders();
//    }
//
//    private void showPlaceholders(){
//        mBroccoli.show();
//        mHandler.removeCallbacks(task);
//        mHandler.postDelayed(task,2000);
//    }
//
//    private Runnable task = new Runnable() {
//        @Override
//        public void run() {
//            showData();
//        }
//    };
//
//    private void showData() {
//        mBroccoli.clearAllPlaceholders();
//    }
//
//    public void onRetry(View view) {
//        showPlaceholders();
//    }


}
