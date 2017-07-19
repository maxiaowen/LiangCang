package com.atguigu.liangcang.shop.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.atguigu.liangcang.R;
import com.atguigu.liangcang.base.BaseFragment;
import com.atguigu.liangcang.shop.bean.PurchaseBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/19.
 */

public class MallFragment extends BaseFragment {


    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.btn_after)
    Button btnAfter;
    
    private Bundle bundle;

    @Override
    public View initView() {

        View view = View.inflate(context, R.layout.fragment_mall, null);
        ButterKnife.bind(this, view);
        bundle = getArguments();
        return view;
    }

    @Override
    public void initData() {

        if(bundle != null) {

            PurchaseBean purchaseBean = (PurchaseBean) bundle.getSerializable("purchaseBean");

            String content = purchaseBean.getData().getItems().getGood_guide().getContent();

            tvContent.setText(content);
        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
