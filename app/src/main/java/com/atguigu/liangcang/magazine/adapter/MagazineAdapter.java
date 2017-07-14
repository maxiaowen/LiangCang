package com.atguigu.liangcang.magazine.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.liangcang.R;
import com.atguigu.liangcang.magazine.bean.Info;
import com.atguigu.liangcang.shop.activity.SpecialActivity;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/12.
 */

public class MagazineAdapter extends RecyclerView.Adapter {

    private final Context context;
    private final List<String> keys;
    private final Map<String, List> map;
    private final TextView tvJul;



    public MagazineAdapter(Context context, Map<String, List> map, List<String> keys, TextView tvJul) {
        this.context = context;
        this.map = map;
        this.keys = keys;
        this.tvJul = tvJul;

    }

    @Override
    public int getItemCount() {
        return keys == null ? 0 : keys.size();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(View.inflate(context, R.layout.item_magazine1, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        Info info = (Info) map.get(keys.get(position)).get(position);
        String str = keys.get(position);
        String substring = str.substring(5);

        List list = map.get(keys.get(position));

        if (position == 0  ) {
            viewHolder.tvToby.setVisibility(View.GONE);
        }
        tvJul.setText(substring);
        viewHolder.tvToby.setText("- " + substring + " -");
        viewHolder.tvContent.setText(info.getTopic_name());
        viewHolder.tvType.setText("- "+info.getCat_name()+" -");

        Picasso.with(context)
                .load(info.getCover_img())
                .placeholder(R.drawable.atguigu_logo)
                .error(R.drawable.atguigu_logo)
                .into(viewHolder.ivIcon);


    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_toby)
        TextView tvToby;
        @Bind(R.id.iv_icon)
        ImageView ivIcon;
        @Bind(R.id.tv_content)
        TextView tvContent;
        @Bind(R.id.tv_type)
        TextView tvType;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Info info = (Info) map.get(keys.get(getLayoutPosition())).get(getLayoutPosition());
                    //跳转到WebView页面Activity
                    Intent intent = new Intent(context, SpecialActivity.class);
                    intent.putExtra("url", info.getTopic_url());
                    context.startActivity(intent);
                }
            });
        }
    }

}



