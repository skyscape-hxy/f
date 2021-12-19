package com.skyscape.demo.frame.ui.activity;



import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.skyscape.demo.frame.R;
import com.skyscape.demo.frame.adapter.MainViewPagerAdapter;
import com.skyscape.demo.frame.base.BaseActivity;
import com.skyscape.demo.frame.base.NormalViewModel;
import com.skyscape.demo.frame.databinding.ActivityMainBinding;


public class MainActivity extends BaseActivity<NormalViewModel, ActivityMainBinding> {


    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void processLogic() {
        initVp();
    }


    @Override
    protected void setListener() {

    }

    private void initVp() {
        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(this);
        binding.viewpager.setAdapter(mainViewPagerAdapter);
        binding.viewpager.setOffscreenPageLimit(mainViewPagerAdapter.getItemCount());
        binding.btmNav.setItemIconTintList(null);
        binding.btmNav.setOnNavigationItemSelectedListener(item -> onTabSelected(item.getItemId()));

        binding.btmNav.setSelectedItemId(R.id.oneFragment);
        //取消BottomNavigationView长按时出现的吐司
        ViewGroup bottomNavigationMenuView = (ViewGroup) binding.btmNav.getChildAt(0);
        for (int position = 0; position < mainViewPagerAdapter.getItemCount(); position++) {
            bottomNavigationMenuView.getChildAt(position).setOnLongClickListener(v -> true);
        }
        binding.viewpager.setUserInputEnabled(false);
    }

    private boolean onTabSelected(int itemId) {
        int position = 0;
        switch (itemId) {
            case R.id.oneFragment:
                position = 0;
                break;
            case R.id.twoFragment:
                position = 1;
                break;
            case R.id.threeFragment:
                position = 2;
                break;
            default:
                break;
        }
        binding.viewpager.setCurrentItem(position, false);
        binding.btmNav.getMenu().getItem(position).setChecked(true);
        return true;
    }

    @Override
    protected boolean setTitle(String titleStr) {
        return false;
    }

}