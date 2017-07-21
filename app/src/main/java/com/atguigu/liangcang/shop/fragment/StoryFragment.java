package com.atguigu.liangcang.shop.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.atguigu.liangcang.R;
import com.atguigu.liangcang.base.BaseFragment;
import com.atguigu.liangcang.shop.bean.ProductBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/21.
 */

public class StoryFragment extends BaseFragment {

    @Bind(R.id.tv_content)
    TextView tvContent;
    private ProductBean productBean;


    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_story, null);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        if (bundle != null) {
            productBean = (ProductBean) bundle.getSerializable("productBean");
        }
        return view;
    }

    @Override
    public void initData() {

        tvContent.setText(productBean.getData().getItems().get(0).getBrand_info().getBrand_desc());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
