package com.atguigu.liangcang.shop.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.atguigu.liangcang.R;
import com.atguigu.liangcang.base.BaseFragment;
import com.atguigu.liangcang.shop.activity.DetailsActivity;
import com.atguigu.liangcang.shop.adapter.TypeAdapter;
import com.atguigu.liangcang.shop.bean.TypeBean;
import com.atguigu.liangcang.utils.UIUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/7/6.
 */

public class TypeFragment extends BaseFragment {

    @Bind(R.id.gv)
    GridView gv;

    private String url = "http://mobile.iliangcang.com/goods/goodsCategory?app_key=Android&sig=430BD99E6C913B8B8C3ED109737ECF15%7C830952120106768&v=1.0";

    private String[] urls = {
            "http://mobile.iliangcang.com/goods/goodsShare?app_key=Android&cat_code=0045&count=10&coverId=1&page=1&sig=3D3968703BE211CC6D931C9D4F737FB4%7C290216330933368&v=1.0",//家居
            "http://mobile.iliangcang.com/goods/goodsShare?app_key=Android&cat_code=0055&count=10&coverId=1&page=1&sig=6E1DEF1DAFF84909ECD98F32FE6CDAD5%7C536890620070968&v=1.0",// 家具
            "http://mobile.iliangcang.com/goods/goodsShare?app_key=Android&cat_code=0062&count=10&coverId=1&page=1&sig=6E1DEF1DAFF84909ECD98F32FE6CDAD5%7C536890620070968&v=1.0",//文具
            "http://mobile.iliangcang.com/goods/goodsShare?app_key=Android&cat_code=0069&count=10&coverId=1&page=1&sig=6E1DEF1DAFF84909ECD98F32FE6CDAD5%7C536890620070968&v=1.0",   //数码
            "http://mobile.iliangcang.com/goods/goodsShare?app_key=Android&cat_code=0077&count=10&coverId=1&page=1&sig=6E1DEF1DAFF84909ECD98F32FE6CDAD5%7C536890620070968&v=1.0",//玩乐
            "http://mobile.iliangcang.com/goods/goodsShare?app_key=Android&cat_code=0082&count=10&coverId=1&page=1&sig=6E1DEF1DAFF84909ECD98F32FE6CDAD5%7C536890620070968&v=1.0",//厨卫
            "http://mobile.iliangcang.com/goods/goodsShare?app_key=Android&cat_code=0092&count=10&coverId=1&page=1&sig=6E1DEF1DAFF84909ECD98F32FE6CDAD5%7C536890620070968&v=1.0",//美食
            "http://mobile.iliangcang.com/goods/goodsShare?app_key=Android&cat_code=0101&count=10&coverId=1&page=1&sig=6E1DEF1DAFF84909ECD98F32FE6CDAD5%7C536890620070968&v=1.0",//男装
            "http://mobile.iliangcang.com/goods/goodsShare?app_key=Android&cat_code=0112&count=10&coverId=1&page=1&sig=6E1DEF1DAFF84909ECD98F32FE6CDAD5%7C536890620070968&v=1.0",//女装
            "http://mobile.iliangcang.com/goods/goodsShare?app_key=Android&cat_code=0125&count=10&coverId=1&page=1&sig=6E1DEF1DAFF84909ECD98F32FE6CDAD5%7C536890620070968&v=1.0",//童装
            "http://mobile.iliangcang.com/goods/goodsShare?app_key=Android&cat_code=0129&count=10&coverId=1&page=1&sig=6E1DEF1DAFF84909ECD98F32FE6CDAD5%7C536890620070968&v=1.0",//鞋包
            "http://mobile.iliangcang.com/goods/goodsShare?app_key=Android&cat_code=0141&count=10&coverId=1&page=1&sig=6E1DEF1DAFF84909ECD98F32FE6CDAD5%7C536890620070968&v=1.0",//配饰
            "http://mobile.iliangcang.com/goods/goodsShare?app_key=Android&cat_code=0154&count=10&coverId=1&page=1&sig=6E1DEF1DAFF84909ECD98F32FE6CDAD5%7C536890620070968&v=1.0",//美护
            "http://mobile.iliangcang.com/goods/goodsShare?app_key=Android&cat_code=0166&count=10&coverId=1&page=1&sig=6E1DEF1DAFF84909ECD98F32FE6CDAD5%7C536890620070968&v=1.0",//户外
            "http://mobile.iliangcang.com/goods/goodsShare?app_key=Android&cat_code=0172&count=10&coverId=1&page=1&sig=6E1DEF1DAFF84909ECD98F32FE6CDAD5%7C536890620070968&v=1.0",//植物
            "http://mobile.iliangcang.com/goods/goodsShare?app_key=Android&cat_code=0182&count=10&coverId=1&page=1&sig=6E1DEF1DAFF84909ECD98F32FE6CDAD5%7C536890620070968&v=1.0",//图书
            "http://mobile.iliangcang.com/goods/goodsShare?app_key=Android&cat_code=0190&count=10&coverId=1&page=1&sig=6E1DEF1DAFF84909ECD98F32FE6CDAD5%7C536890620070968&v=1.0",//礼物
            "http://mobile.iliangcang.com/goods/goodsShare?app_key=Android&cat_code=0198&count=10&coverId=1&page=1&sig=6E1DEF1DAFF84909ECD98F32FE6CDAD5%7C536890620070968&v=1.0",//推荐
            "http://mobile.iliangcang.com/goods/goodsShare?app_key=Android&cat_code=0214&count=10&coverId=1&page=1&sig=6E1DEF1DAFF84909ECD98F32FE6CDAD5%7C536890620070968&v=1.0",//艺术
    };

    private TypeAdapter adapter;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.type_fragment, null);
        ButterKnife.bind(this, view);
        return view;
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
                                Log.e("TAG","联网失败=="+e.getMessage());
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Log.e("TAG","联网成功=="+response);
                                //解析数据
                                parseData(response);
                            }
                        });
            }
        });
    }

    //解析数据
    private void parseData(String json) {
        final TypeBean typeBean = new Gson().fromJson(json, TypeBean.class);

//        Log.e("TAG","name ==" + typeBean.getData().getItems().get(0).getCat_name());
        adapter = new TypeAdapter(context,typeBean.getData().getItems());
        gv.setAdapter(adapter);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                UIUtils.showToast(typeBean.getData().getItems().get(position).getCat_name());

                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("url",urls[position]);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.right_in,R.anim.left_out);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
