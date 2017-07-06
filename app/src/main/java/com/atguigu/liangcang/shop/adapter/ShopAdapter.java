package com.atguigu.liangcang.shop.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.atguigu.liangcang.base.BaseFragment;

import java.util.List;

/**
 * Created by Administrator on 2017/7/6.
 */

public class ShopAdapter extends FragmentPagerAdapter {


    private final List<BaseFragment> fragments;

    private String[] datas = {"分类", "品牌", "首页", "专题", "礼物"};

    public ShopAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;

    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments == null? 0 :fragments.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return datas[position];
    }
}
