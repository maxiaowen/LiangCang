package com.atguigu.liangcang.shop.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atguigu.liangcang.R;
import com.atguigu.liangcang.base.BaseFragment;
import com.atguigu.liangcang.shop.activity.PurchaseActivity;
import com.atguigu.liangcang.shop.adapter.DateilsAdapter;
import com.atguigu.liangcang.shop.bean.DetailsBean;
import com.atguigu.liangcang.shop.bean.PurchaseBean;
import com.atguigu.liangcang.utils.UIUtils;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/7/19.
 */

public class DetailsFragment extends BaseFragment {

    private LinearLayout linearLayout;
    private PurchaseBean purchaseBean;
    private String goods_id;
    private String url;


    @Override
    public View initView() {
        // 创建LinearLayout对象
        linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        Bundle bundle = getArguments();
        if (bundle != null) {
            purchaseBean = (PurchaseBean) bundle.getSerializable("purchaseBean");
//            Log.e("TAG", "DetailsFragment---purchaseBean--name==" + purchaseBean.getData().getItems().getGoods_name());
            goods_id = (String) bundle.get("goods_id");
//            Log.e("TAG","DetailsFragment----goods_id====="+goods_id);
            url = "http://mobile.iliangcang.com/goods/guessLike?app_key=Android&goods_id="+goods_id+"&sig=8EB61B9784B7623223505352E626D648%7C974286010287453&v=1.0";
        }

        return linearLayout;
    }

    @Override
    public void initData() {

        //连网请求数据
        netWorking();
    }

    private void netWorking() {
        //开启分线程
        UIUtils.getGlobalThread().execute(new Runnable() {
            @Override
            public void run() {
                //连网
                OkHttpUtils
                        .get()
                        .url(url)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Log.e("TAG","DetailsFragment---联网失败=="+e.getMessage());
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Log.e("TAG","DetailsFragment---联网成功=="+response);
                                //解析数据
                                parseData(response);
                            }
                        });
            }
        });
    }

    //解析数据
    private void parseData(String json) {
        DetailsBean detailsBean = new Gson().fromJson(json, DetailsBean.class);
//        Log.e("TAG","DetailsFragment-----detailsBean=="+detailsBean.getData().getItems().get(0).getGoods_name());
        setData(detailsBean);
    }


    private void setData(final DetailsBean detailsBean) {
        List<PurchaseBean.DataBean.ItemsBean.GoodsInfoBean> goods_info = purchaseBean.getData().getItems().getGoods_info();

        for (int i = 0; i < goods_info.size(); i++) {

            if (goods_info.get(i).getType() == 1) {

                //添加图片
                ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setPadding(60, 0, 60, 0);
                imageView.setMinimumWidth(goods_info.get(i).getContent().getWidth());
                imageView.setMinimumHeight(goods_info.get(i).getContent().getHeight());

                Picasso.with(context)
                        .load(goods_info.get(i).getContent().getImg())
                        .placeholder(R.drawable.atguigu_logo)
                        .error(R.drawable.atguigu_logo)
                        .into(imageView);

                linearLayout.addView(imageView);

            }
            if (goods_info.get(i).getType() == 0) {

                //添加文字
                TextView textView = new TextView(context);
                textView.setTextSize(12);
                textView.setMinHeight(200);
                textView.setPadding(60, 60, 60, 60);
                textView.setLineSpacing(1, 1);
                textView.setText(goods_info.get(i).getContent().getText());

                linearLayout.addView(textView);
            }

            if (goods_info.get(i).getType() == 2) {

                //添加文字
                TextView textView = new TextView(context);
                textView.setTextSize(15);
                textView.setTextColor(Color.WHITE);
                textView.setMinHeight(200);
                textView.setPadding(60, 60, 60, 60);
                textView.setLineSpacing(1, 1);
                textView.setText(goods_info.get(i).getContent().getText());

                linearLayout.addView(textView);
            }

        }


        View view = View.inflate(context, R.layout.fragment_details, null);

        TextView tv_desc = (TextView) view.findViewById(R.id.tv_desc);
        TextView tv_brand_name = (TextView) view.findViewById(R.id.tv_brand_name);
        TextView tv_brand_desc = (TextView) view.findViewById(R.id.tv_brand_desc);
        TextView tv_reason = (TextView) view.findViewById(R.id.tv_reason);
        GridView gridView = (GridView) view.findViewById(R.id.gridView);
        DateilsAdapter adapter;


        tv_desc.setText(purchaseBean.getData().getItems().getGoods_desc());
        tv_brand_name.setText(purchaseBean.getData().getItems().getBrand_info().getBrand_name());
        tv_brand_desc.setText(purchaseBean.getData().getItems().getBrand_info().getBrand_desc());
        tv_reason.setText(purchaseBean.getData().getItems().getRec_reason());

        adapter =  new DateilsAdapter(context,detailsBean.getData().getItems());

        gridView.setAdapter(adapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(context, PurchaseActivity.class);
                intent.putExtra("goods_id",detailsBean.getData().getItems().get(position).getGoods_id());
                startActivity(intent);
            }
        });


        linearLayout.addView(view);
    }


}
