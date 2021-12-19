package com.skyscape.demo.frame.utils;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.skyscape.demo.frame.R;

/**
 * @author: Administrator
 * @date: 2021/6/10
 * @description
 */
public class DataBindingUtil {

    @BindingAdapter(value = {"imageUrl", "defPic"}, requireAll = false)
    public static void loadImage(ImageView imageView, String imageUrl, int defPic) {
        int zwt = R.drawable.ic_cy_zwt;
        if (defPic == 0) {
            defPic = zwt;
        }
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .error(defPic)
                .placeholder(defPic)
                .into(imageView);
    }
    @BindingAdapter("imageSrc")
    public static void loadImage(ImageView imageView, int id) {
        imageView.setImageResource(id);
    }
}