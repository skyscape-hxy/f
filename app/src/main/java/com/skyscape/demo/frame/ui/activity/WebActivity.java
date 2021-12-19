package com.skyscape.demo.frame.ui.activity;

import com.skyscape.demo.frame.R;
import com.skyscape.demo.frame.base.BaseActivity;
import com.skyscape.demo.frame.base.NormalViewModel;
import com.skyscape.demo.frame.databinding.ActivityWebBinding;


/**
 * Created by leo
 * on 2019/11/12.
 */
public class WebActivity extends BaseActivity<NormalViewModel, ActivityWebBinding> {
    @Override
    protected int getContentViewId() {
        return R.layout.activity_web;
    }

    @Override
    protected void processLogic() {
        String url = getIntent().getStringExtra("url");
        binding.webViewX5.setTitleBar(binding.titleBar);
        binding.webViewX5.loadUrl(url);

    }


    @Override
    protected void onResume() {
        super.onResume();
        binding.webViewX5.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        binding.webViewX5.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.webViewX5.destroy();
    }

    @Override
    protected void setListener() {
        binding.titleBar.bar_left_btn.setOnClickListener(v -> onBackPressed());
    }

    @Override
    protected boolean setTitle(String titleStr) {
        return false;
    }

    @Override
    public void onBackPressed() {
        if (binding.webViewX5.canGoBack()) {
            binding.webViewX5.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
