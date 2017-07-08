package com.atguigu.liangcang.shop.activity;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.atguigu.liangcang.R;
import com.atguigu.liangcang.base.BaseActivity;
import com.atguigu.liangcang.shop.adapter.DateilsAdapter;
import com.atguigu.liangcang.shop.bean.DetailsBean;
import com.atguigu.liangcang.utils.UIUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import okhttp3.Call;


public class DetailsActivity extends BaseActivity {


    @Bind(R.id.gridView)
    GridView gridView;

    private String url;

    private DateilsAdapter adapter;

    @Override
    public void initListener() {

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
                                Log.e("TAG", "联网失败==" + e.getMessage());
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Log.e("TAG", "联网成功==" + response);
                                //解析数据
                                parseData(response);
                            }
                        });
            }
        });
    }

    //解析数据
    private void parseData(String json) {
        final DetailsBean detailsBean = new Gson().fromJson(json, DetailsBean.class);

        Log.e("TAG", "name ==" + detailsBean.getData().getItems().get(0).getGoods_name());
        adapter = new DateilsAdapter(DetailsActivity.this, detailsBean.getData().getItems());
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UIUtils.showToast(detailsBean.getData().getItems().get(position).getGoods_name());

            }
        });

    }


    @Override
    public void initView() {
        url = getIntent().getStringExtra("url");

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_details;
    }

}
