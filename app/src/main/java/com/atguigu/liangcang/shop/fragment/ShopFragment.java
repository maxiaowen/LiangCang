package com.atguigu.liangcang.shop.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.atguigu.liangcang.R;
import com.atguigu.liangcang.activity.MainActivity;
import com.atguigu.liangcang.base.BaseFragment;
import com.atguigu.liangcang.shop.adapter.ShopAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/6.
 */

public class ShopFragment extends BaseFragment {

    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;

    private List<BaseFragment> fragments;
    private ShopAdapter adapter;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.pager_shop, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {

        fragments = new ArrayList<>();
        fragments.add(new TypeFragment());
        fragments.add(new BrandFragment());
        fragments.add(new HomeFragment());
        fragments.add(new SpecialFragment());
        fragments.add(new GiftFragment());

        MainActivity mainActivity = (MainActivity) context;
        adapter = new ShopAdapter(mainActivity.getSupportFragmentManager(),fragments);
        viewpager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewpager);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
