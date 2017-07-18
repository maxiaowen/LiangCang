package com.atguigu.liangcang.magazine.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.atguigu.liangcang.R;
import com.atguigu.liangcang.base.BaseActivity;
import com.atguigu.liangcang.base.BaseFragment;
import com.atguigu.liangcang.magazine.adapter.HeadAdapter;
import com.atguigu.liangcang.magazine.fragment.AuthorFragment;
import com.atguigu.liangcang.magazine.fragment.ClassifyFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class MagazineActivity extends BaseActivity {


    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.rl_up)
    RelativeLayout rlUp;
    @Bind(R.id.activity_magazine)
    LinearLayout activityMagazine;

    private HeadAdapter adapter;

    private List<BaseFragment> fragments;

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

        fragments = new ArrayList<>();

        fragments.add(new ClassifyFragment());
        fragments.add(new AuthorFragment());

        adapter = new HeadAdapter(this.getSupportFragmentManager(),fragments);

        viewpager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewpager);

    }

    @Override
    public void initListener() {

        rlUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_magazine;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
