package com.atguigu.liangcang.shop.fragment;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.atguigu.liangcang.base.BaseFragment;

/**
 * Created by Administrator on 2017/7/21.
 */

public class ProductFragment extends BaseFragment{


    @Override
    public View initView() {
        TextView textView = new TextView(context);
        textView.setText("品牌产品");
        textView.setTextSize(30);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    @Override
    public void initData() {

    }
}
