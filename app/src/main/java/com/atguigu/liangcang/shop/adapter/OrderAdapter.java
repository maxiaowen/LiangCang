package com.atguigu.liangcang.shop.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atguigu.liangcang.R;
import com.atguigu.liangcang.shop.bean.PurchaseBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/25.
 */

public class OrderAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<PurchaseBean> datas;

    public OrderAdapter(Context orderActivity, ArrayList<PurchaseBean> datas) {
        this.context = orderActivity;
        this.datas = datas;

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

        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_order, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        PurchaseBean purchaseBean = datas.get(position);
        PurchaseBean.DataBean.ItemsBean itemsBean = purchaseBean.getData().getItems();

        viewHolder.tvItemName1.setText(itemsBean.getGoods_name());
        viewHolder.tvItemPrice.setText(itemsBean.getPrice());
        viewHolder.tvItemNumber.setText("X"+itemsBean.getNumber());



        if(itemsBean.getSku_info().size() == 1) {
            viewHolder.tvTypeName1.setText(itemsBean.getSku_info().get(0).getType_name()+"：");
            viewHolder.tvAttrName1.setText(itemsBean.getSku_info().get(0).getAttrList().get(0).getAttr_name()+"；");
        }else {
            viewHolder.tvTypeName1.setText(itemsBean.getSku_info().get(0).getType_name()+"：");
            viewHolder.tvAttrName1.setText(itemsBean.getSku_info().get(0).getAttrList().get(0).getAttr_name()+"；");

            viewHolder.tvTypeName2.setText(itemsBean.getSku_info().get(1).getType_name()+"：");
            viewHolder.tvAttrName2.setText(itemsBean.getSku_info().get(1).getAttrList().get(0).getAttr_name()+"；");
            viewHolder.tvTypeName1.setVisibility(View.VISIBLE);
            viewHolder.tvAttrName1.setVisibility(View.VISIBLE);
        }

        Picasso.with(context).load(itemsBean.getGoods_image()).into(viewHolder.ivItem);


        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.iv_item)
        ImageView ivItem;
        @Bind(R.id.tv_item_name1)
        TextView tvItemName1;
        @Bind(R.id.tv_type_name1)
        TextView tvTypeName1;
        @Bind(R.id.tv_attr_name1)
        TextView tvAttrName1;
        @Bind(R.id.tv_type_name2)
        TextView tvTypeName2;
        @Bind(R.id.tv_attr_name2)
        TextView tvAttrName2;
        @Bind(R.id.tv_item_price)
        TextView tvItemPrice;
        @Bind(R.id.tv_item_number)
        TextView tvItemNumber;
        @Bind(R.id.ll_item1)
        LinearLayout llItem1;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
