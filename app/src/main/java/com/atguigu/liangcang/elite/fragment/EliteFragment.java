package com.atguigu.liangcang.elite.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.atguigu.liangcang.R;
import com.atguigu.liangcang.base.BaseFragment;
import com.atguigu.liangcang.elite.adapter.EliteAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/6.
 */

public class EliteFragment extends BaseFragment {

    @Bind(R.id.base_title)
    TextView baseTitle;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    private ArrayList<BaseFragment> fragments;

    private EliteAdapter adapter;


    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.pager_elite, null);
        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void initTitle() {
        super.initTitle();
        baseTitle.setText("精华");
    }

    @Override
    public void initData() {


        fragments = new ArrayList<>();
        fragments.add(new RecommendFragment());
        fragments.add(new CrossTtalkFragment());


        adapter = new EliteAdapter(getChildFragmentManager(), fragments);

        viewpager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewpager);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
