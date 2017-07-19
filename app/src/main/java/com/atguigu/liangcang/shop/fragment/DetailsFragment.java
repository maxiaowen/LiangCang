package com.atguigu.liangcang.shop.fragment;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.atguigu.liangcang.base.BaseFragment;

/**
 * Created by Administrator on 2017/7/19.
 */

public class DetailsFragment extends BaseFragment {

    private TextView textView;


    @Override
    public View initView() {

        textView = new TextView(context);

        textView.setTextColor(Color.RED);
        textView.setTextSize(30);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    @Override
    public void initData() {

        textView.setText("我是商品详情");
    }
}
