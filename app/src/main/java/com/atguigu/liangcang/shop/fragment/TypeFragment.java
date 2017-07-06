package com.atguigu.liangcang.shop.fragment;

import android.view.View;
import android.widget.GridView;

import com.atguigu.liangcang.R;
import com.atguigu.liangcang.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/6.
 */

public class TypeFragment extends BaseFragment {

    @Bind(R.id.gv)
    GridView gv;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.type_fragment, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
