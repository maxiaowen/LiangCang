package com.atguigu.liangcang.magazine.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.liangcang.R;
import com.atguigu.liangcang.magazine.bean.AuthorBean;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/18.
 */

public class AuthorAdapter extends BaseAdapter {
    private final Context context;
    private final List<AuthorBean.DataBean.ItemsBean> datas;

    public AuthorAdapter(Context context, List<AuthorBean.DataBean.ItemsBean> items) {
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

        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_author, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        AuthorBean.DataBean.ItemsBean itemsBean = datas.get(position);

        viewHolder.tvName.setText(itemsBean.getAuthor_name());
        viewHolder.tvNote.setText(itemsBean.getNote());

        Picasso.with(context)
                .load(itemsBean.getThumb())
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
        @Bind(R.id.tv_note)
        TextView tvNote;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
