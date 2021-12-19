package com.skyscape.demo.frame.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.skyscape.demo.frame.ui.fragment.one.OneFragment;
import com.skyscape.demo.frame.ui.fragment.three.ThreeFragment;
import com.skyscape.demo.frame.ui.fragment.two.TwoFragment;

import java.util.Arrays;
import java.util.List;

/**
 * @author: Administrator
 * @date: 2021/5/25
 * @description
 */
public class MainViewPagerAdapter extends FragmentStateAdapter {
    private  final List<Fragment> FRAGMENT_LIST = Arrays.asList(new OneFragment(),
            new TwoFragment(), new ThreeFragment());

    public MainViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return FRAGMENT_LIST.get(position);
    }

    @Override
    public int getItemCount() {
        return FRAGMENT_LIST.size();
    }


}