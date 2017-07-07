package com.atguigu.liangcang.shop.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.atguigu.liangcang.R;
import com.atguigu.liangcang.base.BaseFragment;
import com.atguigu.liangcang.shop.activity.SpecialActivity;
import com.atguigu.liangcang.shop.adapter.SpecialAdapter;
import com.atguigu.liangcang.shop.bean.SpecialBean;
import com.atguigu.liangcang.utils.UIUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/7/6.
 */

public class SpecialFragment extends BaseFragment {

    private ListView listView;
    private String url = "http://mobile.iliangcang.com/goods/shopSpecial?app_key=Android&count=10&page=1&sig=3780CB0808528F7CE99081D295EE8C0F%7C116941220826768&uid=626138098&user_token=0516ed9429352c8e1e3bd11c63ba6f54&v=1.0";
    private SpecialAdapter adapter;


    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_special,null);
        listView = (ListView) view.findViewById(R.id.listView);
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
                                Log.e("TAG","SpecialFragment联网失败=="+e.getMessage());
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Log.e("TAG","SpecialFragment联网成功=="+response);
//                                //解析数据
                                parseData(response);
                            }
                        });
            }
        });
    }

    //解析数据
    private void parseData(String json) {
        final SpecialBean specialBean = new Gson().fromJson(json, SpecialBean.class);

//        Log.e("TAG","name ==" + brandBean.getData().getItems().get(0).getBrand_name());
        adapter = new SpecialAdapter(context,specialBean.getData().getItems());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UIUtils.showToast(specialBean.getData().getItems().get(position).getTopic_name());

                Intent intent = new Intent(context, SpecialActivity.class);
                intent.putExtra("url",specialBean.getData().getItems().get(position).getTopic_url());
                startActivity(intent);

            }
        });

    }
}
