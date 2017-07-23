package com.atguigu.liangcang.shop.fragment;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.liangcang.R;
import com.atguigu.liangcang.base.BaseFragment;
import com.atguigu.liangcang.shop.activity.ShoppingcartActivity;
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
    @Bind(R.id.base_search)
    ImageView baseSearch;
    @Bind(R.id.base_cart)
    ImageView baseCart;
    @Bind(R.id.base_title)
    TextView baseTitle;

    private List<BaseFragment> fragments;
    private ShopAdapter adapter;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.pager_shop, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initTitle() {
        super.initTitle();
        baseCart.setVisibility(View.VISIBLE);
        baseSearch.setVisibility(View.VISIBLE);
        baseTitle.setText("商店");
    }

    @Override
    public void initData() {

        fragments = new ArrayList<>();
        fragments.add(new TypeFragment());
        fragments.add(new BrandFragment());
        fragments.add(new HomeFragment());
        fragments.add(new SpecialFragment());
        fragments.add(new GiftFragment());

        adapter = new ShopAdapter(getChildFragmentManager(), fragments);
        viewpager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewpager);
    }


    @Override
    public void initListener() {
        super.initListener();
        //点击进入购物车
        baseCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShoppingcartActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
