package com.atguigu.liangcang.custom.fragment;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.atguigu.liangcang.base.BaseFragment;

/**
 * Created by Administrator on 2017/7/6.
 */

public class CustomFragment extends BaseFragment {
    TextView textView;
    @Override
    public View initView() {
        textView = new TextView(context);
        textView.setTextSize(30);
        textView.setTextColor(Color.RED);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    @Override
    public void initData() {

        textView.setText("自定义");
    }
}
