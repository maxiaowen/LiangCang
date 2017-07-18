package com.atguigu.liangcang.magazine.fragment;

import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.atguigu.liangcang.R;
import com.atguigu.liangcang.base.BaseFragment;
import com.atguigu.liangcang.magazine.adapter.AuthorAdapter;
import com.atguigu.liangcang.magazine.bean.AuthorBean;
import com.atguigu.liangcang.utils.UIUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/7/14.
 */

public class AuthorFragment extends BaseFragment {

    private ListView listView;

    private AuthorAdapter adapter;

    private String url = "http://mobile.iliangcang.com/topic/magazineAuthorList?app_key=Android&sig=2FA0974FFF1BC3DFA562AA63C8B5A84F%7C118265010131868&v=1.0";


    @Override
    public View initView() {

        View view = View.inflate(context, R.layout.fragment_author, null);
        listView = (ListView) view.findViewById(R.id.listView);
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
                                Log.e("TAG", "AuthorFragment联网失败==" + e.getMessage());
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Log.e("TAG", "AuthorFragment联网成功==" + response);
                                //解析数据
                                parseData(response);
                            }
                        });
            }
        });
    }

    //解析数据
    private void parseData(String json) {
        AuthorBean authorBean = new Gson().fromJson(json, AuthorBean.class);

        Log.e("TAG", "AuthorBean__name ==" + authorBean.getData().getItems().get(0).getNote());

        adapter = new AuthorAdapter(context,authorBean.getData().getItems());
        listView.setAdapter(adapter);

    }

}
