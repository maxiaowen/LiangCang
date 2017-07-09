package com.atguigu.liangcang.daren.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
    @Bind(R.id.base_select)
    ImageView baseSelect;
    @Bind(R.id.base_close)
    ImageView baseClose;
    @Bind(R.id.base_title)
    TextView baseTitle;
    @Bind(R.id.base_search)
    ImageView baseSearch;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_daren, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initTitle() {
        super.initTitle();
        baseSearch.setVisibility(View.VISIBLE);
        baseSelect.setVisibility(View.VISIBLE);
        baseTitle.setText("达人");
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
