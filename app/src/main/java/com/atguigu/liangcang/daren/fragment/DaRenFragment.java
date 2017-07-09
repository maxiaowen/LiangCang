package com.atguigu.liangcang.daren.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.atguigu.liangcang.R;
import com.atguigu.liangcang.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/6.
 */

public class DaRenFragment extends BaseFragment {
    @Bind(R.id.rv_daren)
    RecyclerView rvDaren;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_daren, null);
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
