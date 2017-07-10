package com.atguigu.liangcang.elite.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.atguigu.liangcang.base.BaseFragment;

import java.util.List;

/**
 * Created by Administrator on 2017/7/10.
 */

public class EliteAdapter extends FragmentStatePagerAdapter {


    private final List<BaseFragment> fragments;

    private String[] datas = {"推荐","段子"};

    public EliteAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);

        Log.e("TAG","Fragements=="+fragments.get(0));
        this.fragments = fragments;

    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return  fragments.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return datas[position];

    }
}
