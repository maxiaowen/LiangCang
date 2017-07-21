package com.atguigu.liangcang.shop.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.liangcang.R;
import com.atguigu.liangcang.shop.bean.ProductBean;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/21.
 */

public class ProductAdapter extends BaseAdapter {
    private final Context context;
    private final List<ProductBean.DataBean.ItemsBean> datas;

    public ProductAdapter(Context context, List<ProductBean.DataBean.ItemsBean> items) {
        this.context = context;
        this.datas = items;
    }

    @Override
    public int getCount() {
        return datas == null? 0 : datas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DateilsAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_details, null);
            viewHolder = new DateilsAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (DateilsAdapter.ViewHolder) convertView.getTag();
        }

        ProductBean.DataBean.ItemsBean itemsBean = datas.get(position);

        viewHolder.tvName.setText(itemsBean.getGoods_name());
        viewHolder.tvBrand.setText(itemsBean.getBrand_info().getBrand_name());
        viewHolder.tvGood.setText(itemsBean.getLike_count());
        viewHolder.tvDiscountPrice.setText(itemsBean.getDiscount_price());
        viewHolder.tvPrice.setText(itemsBean.getPrice());
        Picasso.with(context)
                .load(itemsBean.getGoods_image())
                .placeholder(R.drawable.atguigu_logo )
                .error(R.drawable.atguigu_logo )
                .into(viewHolder.image);


        return convertView;
    }


    static class ViewHolder {
        @Bind(R.id.image)
        ImageView image;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_brand)
        TextView tvBrand;
        @Bind(R.id.tv_good)
        TextView tvGood;
        @Bind(R.id.tv_discount_price)
        TextView tvDiscountPrice;
        @Bind(R.id.tv_price)
        TextView tvPrice;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
