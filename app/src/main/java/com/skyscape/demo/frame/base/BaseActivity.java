package com.skyscape.demo.frame.base;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.google.gson.JsonSyntaxException;
import com.gyf.immersionbar.ImmersionBar;
import com.skyscape.demo.frame.R;
import com.skyscape.demo.frame.bean.basebean.Resource;
import com.skyscape.demo.frame.ui.activity.MainActivity;
import com.skyscape.demo.frame.ui.customview.CustomProgress;
import com.skyscape.demo.frame.ui.customview.TitileBar;

import com.skyscape.demo.frame.utils.ToastUtils;
import com.skyscape.demo.frame.utils.networks.NetWorkUtils;
import com.trello.rxlifecycle2.components.support.RxFragmentActivity;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Map;


public abstract class BaseActivity<VM extends BaseViewModel, VDB extends ViewDataBinding>
        extends RxFragmentActivity {
    protected VM mViewModel;
    protected VDB binding;

    private CustomProgress dialog;
    private LinearLayout mContainer;
    protected TitileBar mTitileBar;
    private String mTitleStr;

    //获取当前activity布局文件,并初始化我们的binding
    protected abstract int getContentViewId();

    //处理逻辑业务
    protected abstract void processLogic();

    //所有监听放这里
    protected abstract void setListener();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        //databinding绑定
        initDataBinding();
        //初始化状态栏
        initStatusBar();
        createViewModel();
        processLogic();
        setListener();
        //私有的ViewModel与View的契约事件回调逻辑
        registorUIChangeLiveDataCallBack();
    }

    private void initDataBinding() {
        mContainer = findViewById(R.id.container);

        initTitle();
        try {
            binding = DataBindingUtil.inflate(getLayoutInflater(), getContentViewId(), mContainer, true);
            //给binding加上感知生命周期
            binding.setLifecycleOwner(this);
        } catch (Exception e) {
            getLayoutInflater().inflate(getContentViewId(), mContainer);
        }
    }

    //初始化标题栏
    private void initTitle() {
        if (setTitle(null)) {
            mTitileBar = new TitileBar(getContext());
            mTitileBar.setBackColor(getResources().getColor(R.color.status_background));
            mTitileBar.setTitleColor(getResources().getColor(R.color.white));
            mTitileBar.setTitleStr(mTitleStr);
            mContainer.addView(mTitileBar);
        } else {
            mTitileBar = null;
        }
    }

    //是否设置标题
    protected boolean setTitle(String titleStr) {
        this.mTitleStr = titleStr;
        return true;
    }

    protected void registorUIChangeLiveDataCallBack() {
        mViewModel.getUc().getStartActivityEvent().observe(this, new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(Map<String, Object> params) {
                Class<?> clz = (Class<?>) params.get(BaseViewModel.ParameterField.CLASS);
                Bundle bundle = (Bundle) params.get(BaseViewModel.ParameterField.BUNDLE);
                startActivity(clz, bundle);
            }
        });
        //关闭界面
        mViewModel.getUc().getFinishEvent().observe(this, (Observer<Void>) v -> finish());
    }

    public Context getContext() {
        return this;
    }

    protected void initStatusBar() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarDarkFont(true)//状态栏字体是深色，不写默认为亮色
                .statusBarColor(R.color.white)//状态栏颜色，不写默认透明色
                .init();
//        沉浸控件 xml布局参考immerse_sample.xml
//        ImmersionBar.with(this)
//                .statusBarView(R.id.top_view)
//                .fullScreen(true)
//                .init();
    }

    private void createViewModel() {
        if (mViewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseViewModel.class;
            }
            mViewModel = (VM) new ViewModelProvider(this).get(modelClass);
            mViewModel.setObjectLifecycleTransformer(bindToLifecycle());
        }
    }

    public abstract class OnCallback<T> implements Resource.OnHandleCallback<T> {

        @Override
        public void onLoading(String msg) {
            if (dialog == null) {
                dialog = CustomProgress.show(BaseActivity.this, "",
                        true, null);
            }
            if (TextUtils.isEmpty(msg)) {
                dialog.setMessage(msg);
            }
            if (!dialog.isShowing() && !BaseActivity.this.isFinishing()) {
                dialog.show();
            }
        }

        @Override
        public void onFailure(String msg) {
            ToastUtils.showToast(msg);
        }

        @Override
        public void onError(Throwable throwable) {
            if (!NetWorkUtils.isNetworkConnected(getContext())) {
                ToastUtils.showToast(getString(R.string.result_network_error));
                return;
            }
            if (throwable instanceof ConnectException) {
                ToastUtils.showToast(getString(R.string.result_server_error));
            } else if (throwable instanceof SocketTimeoutException) {
                ToastUtils.showToast(getString(R.string.result_server_timeout));
            } else if (throwable instanceof JsonSyntaxException) {
                ToastUtils.showToast(getString(R.string.data_parsing_error));
            } else {
                ToastUtils.showToast(getString(R.string.result_empty_error));
            }

        }

        @Override
        public void onCompleted() {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        }

        @Override
        public void onProgress(int precent, long total) {

        }
    }


    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }




}