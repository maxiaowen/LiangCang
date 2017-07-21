package com.atguigu.liangcang.shop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.atguigu.liangcang.R;
import com.atguigu.liangcang.base.BaseFragment;
import com.atguigu.liangcang.shop.activity.PurchaseActivity;
import com.atguigu.liangcang.shop.adapter.ProductAdapter;
import com.atguigu.liangcang.shop.bean.ProductBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/21.
 */

public class ProductFragment extends BaseFragment {

    @Bind(R.id.gridView)
    GridView gridView;
    private ProductBean productBean;
    private ProductAdapter adapter;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_product, null);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        if (bundle != null) {
            productBean = (ProductBean) bundle.getSerializable("productBean");
        }
        return view;
    }

    @Override
    public void initData() {

        final List<ProductBean.DataBean.ItemsBean> items = productBean.getData().getItems();
        adapter = new ProductAdapter(context,items);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(context, PurchaseActivity.class);
                intent.putExtra("goods_id",items.get(position).getGoods_id());
                startActivity(intent);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
