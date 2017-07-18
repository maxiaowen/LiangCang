package com.atguigu.liangcang.magazine.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.atguigu.liangcang.base.BaseFragment;

import java.util.List;

/**
 * Created by Administrator on 2017/7/18.
 */

public class HeadAdapter extends FragmentPagerAdapter {

    private final List<BaseFragment> fragments;

    private String[] datas = {"分类","作者"};

    public HeadAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return datas[position];
    }
}
