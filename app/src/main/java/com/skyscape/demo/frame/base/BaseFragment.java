package com.skyscape.demo.frame.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.JsonSyntaxException;
import com.skyscape.demo.frame.R;
import com.skyscape.demo.frame.bean.basebean.Resource;
import com.skyscape.demo.frame.bean.imp.StatusLayoutCallback;
import com.skyscape.demo.frame.ui.customview.CustomProgress;
import com.skyscape.demo.frame.ui.customview.TitileBar;

import com.skyscape.demo.frame.utils.ToastUtils;
import com.skyscape.demo.frame.utils.networks.NetWorkUtils;
import com.trello.rxlifecycle2.components.support.RxFragment;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Map;

/**
 * @author: Administrator
 * @date: 2021/5/24
 * @description
 */
public abstract class BaseFragment<VM extends BaseViewModel, VDB extends ViewDataBinding>
        extends RxFragment {
    protected VM mViewModel;
    protected View mContentView;
    protected VDB binding;
    protected BaseActivity mActivity;
    //    private CustomProgress dialog;
    protected TitileBar mTitileBar;
    private String mTitleStr;
    private LinearLayout mParent;
    private CustomProgress dialog;

    //获取当前fragment布局文件
    protected abstract int getContentViewId();

    //处理逻辑业务
    protected abstract void processLogic(Bundle savedInstanceState);

    //所有监听放这里
    protected abstract void setListener();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (BaseActivity) activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mContentView == null) {
            View view = inflater.inflate(R.layout.fragment_base, container, false);
            mParent = view.findViewById(R.id.container);

            initTitle();
            binding = DataBindingUtil.inflate(inflater, getContentViewId(), mParent, true);
            mContentView = binding.getRoot();
            binding.setLifecycleOwner(this);
            createViewModel();
            setListener();
            processLogic(savedInstanceState);
            //私有的ViewModel与View的契约事件回调逻辑
            registorUIChangeLiveDataCallBack();
        } else {
            ViewGroup parent = (ViewGroup) mContentView.getParent();
            if (parent != null) {
                parent.removeView(mContentView);
            }
        }
        return mContentView;

    }


    //初始化标题栏
    private void initTitle() {
        if (setTitle(null)) {
            mTitileBar = new TitileBar(getContext());
            mTitileBar.setBackColor(getResources().getColor(R.color.status_background));
            mTitileBar.setTitleColor(getResources().getColor(R.color.white));
            mTitileBar.setTitleStr(mTitleStr);
            mParent.addView(mTitileBar);
        } else {
            mTitileBar = null;
        }
    }

    //是否设置标题
    protected boolean setTitle(String titleStr) {
        this.mTitleStr = titleStr;
        return false;
    }


    protected void registorUIChangeLiveDataCallBack() {
        mViewModel.getUc().getStartActivityEvent().observe(getViewLifecycleOwner(), (Observer<Map<String, Object>>) params -> {
            Class<?> clz = (Class<?>) params.get(BaseViewModel.ParameterField.CLASS);
            Bundle bundle = (Bundle) params.get(BaseViewModel.ParameterField.BUNDLE);
            startActivity(clz, bundle);
        });
        //关闭界面
        mViewModel.getUc().getFinishEvent().observe(getViewLifecycleOwner(), (Observer<Void>) v -> getActivity().finish());
    }

    public void createViewModel() {
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
                dialog = CustomProgress.show(mActivity, "", true, null);
            }

            if (!TextUtils.isEmpty(msg)) {
                dialog.setMessage(msg);
            }

            if (!dialog.isShowing()) {
                dialog.show();
            }
        }

        @Override
        public void onError(Throwable throwable) {

            if (!NetWorkUtils.isNetworkConnected(getContext())) {
                ToastUtils.showToast(getContext().getResources().getString(R.string.result_network_error));
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
        public void onFailure(String msg) {
            ToastUtils.showToast(msg);
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
        Intent intent = new Intent(getContext(), clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }




}