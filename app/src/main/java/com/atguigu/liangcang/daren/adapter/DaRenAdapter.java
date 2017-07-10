package com.atguigu.liangcang.daren.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.liangcang.R;
import com.atguigu.liangcang.daren.bean.DaRenBean;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/9.
 */

public class DaRenAdapter extends RecyclerView.Adapter {

    private final Context context;
    private final List<DaRenBean.DataBean.ItemsBean> datas;


    public DaRenAdapter(Context context, List<DaRenBean.DataBean.ItemsBean> items) {
        this.context = context;
        this.datas = items;
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewHolder holder = new ViewHolder(View.inflate(context, R.layout.item_daren, null));

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ViewHolder viewHolder = (ViewHolder) holder;

        DaRenBean.DataBean.ItemsBean itemsBean = datas.get(position);

        viewHolder.tvName.setText(itemsBean.getUsername());
        viewHolder.tvType.setText(itemsBean.getDuty());
        Picasso.with(context)
                .load(itemsBean.getUser_images().getOrig())
                .placeholder(R.drawable.atguigu_logo)
                .error(R.drawable.atguigu_logo)
                .into(viewHolder.ivIcon);


    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_icon)
        ImageView ivIcon;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_type)
        TextView tvType;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(getLayoutPosition());
                }
            });
        }
    }


    private OnItemClickListener onItemClickListener;


    public interface OnItemClickListener {

        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


}
