package com.atguigu.liangcang.shop.fragment;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.atguigu.liangcang.R;
import com.atguigu.liangcang.base.BaseFragment;
import com.atguigu.liangcang.shop.adapter.BrandAdapter;
import com.atguigu.liangcang.shop.bean.BrandBean;
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

public class BrandFragment extends BaseFragment {


    @Bind(R.id.listView)
    ListView listView;

    private String url = "http://mobile.iliangcang.com/brand/brandList?app_key=Android&count=20&page=1&sig=430BD99E6C913B8B8C3ED109737ECF15%7C830952120106768&v=1.0";

    private BrandAdapter adapter;

    @Override
    public View initView() {

        View view = View.inflate(context, R.layout.fragment_brand, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        //联网
        netWorking();
    }

    private void netWorking() {

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
                                Log.e("TAG","BrandFragment联网失败=="+e.getMessage());
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Log.e("TAG","BrandFragment联网成功=="+response);
                                //解析数据
                                parseData(response);
                            }
                        });
            }
        });
    }

    //解析数据
    private void parseData(String json) {
        final BrandBean brandBean = new Gson().fromJson(json, BrandBean.class);

//        Log.e("TAG","name ==" + brandBean.getData().getItems().get(0).getBrand_name());
        adapter = new BrandAdapter(context,brandBean.getData().getItems());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UIUtils.showToast(brandBean.getData().getItems().get(position).getBrand_name());


            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
