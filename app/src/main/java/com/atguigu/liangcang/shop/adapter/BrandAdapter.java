package com.atguigu.liangcang.shop.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.liangcang.R;
import com.atguigu.liangcang.shop.bean.BrandBean;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/7.
 */

public class BrandAdapter extends BaseAdapter {
    private final Context context;
    private final List<BrandBean.DataBean.ItemsBean> datas;

    public BrandAdapter(Context context, List<BrandBean.DataBean.ItemsBean> items) {
        this.context = context;
        this.datas = items;
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
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
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_brand, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        BrandBean.DataBean.ItemsBean itemsBean = datas.get(position);

        viewHolder.tvName.setText(itemsBean.getBrand_name());
        Picasso.with(context)
                .load(itemsBean.getBrand_logo())
                .placeholder(R.drawable.atguigu_logo )
                .error(R.drawable.atguigu_logo )
                .into(viewHolder.ivIcon);


        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.iv_icon)
        ImageView ivIcon;
        @Bind(R.id.tv_name)
        TextView tvName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
