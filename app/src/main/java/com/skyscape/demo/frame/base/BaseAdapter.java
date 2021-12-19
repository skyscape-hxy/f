package com.skyscape.demo.frame.base;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

import org.jetbrains.annotations.NotNull;

import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * @author: Administrator
 * @date: 2021/6/30
 * @description
 */
public class BaseAdapter<T>  extends BindingRecyclerViewAdapter<T> {
    @NonNull
    @NotNull
    @Override
    public ViewDataBinding onCreateBinding(@NonNull @NotNull LayoutInflater inflater, int layoutId, @NonNull @NotNull ViewGroup viewGroup) {
        return super.onCreateBinding(inflater, layoutId, viewGroup);
    }

    @Override
    public void onBindBinding(@NonNull @NotNull ViewDataBinding binding, int variableId, int layoutRes, int position, T item) {
        super.onBindBinding(binding, variableId, layoutRes, position, item);

    }

    @Override
    public void setItemBinding(@NonNull @NotNull ItemBinding<? super T> itemBinding) {
        super.setItemBinding(itemBinding);
    }
}