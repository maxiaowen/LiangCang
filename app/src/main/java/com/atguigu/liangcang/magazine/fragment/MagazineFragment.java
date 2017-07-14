package com.atguigu.liangcang.magazine.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.atguigu.liangcang.R;
import com.atguigu.liangcang.base.BaseFragment;
import com.atguigu.liangcang.magazine.adapter.MagazineAdapter;
import com.atguigu.liangcang.magazine.bean.Info;
import com.atguigu.liangcang.magazine.bean.MagazineBean;
import com.atguigu.liangcang.utils.UIUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/7/6.
 */

public class MagazineFragment extends BaseFragment {
    @Bind(R.id.tv_jul)
    TextView tvJul;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.base_title)
    TextView baseTitle;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.rv_title)
    RelativeLayout rvTitle;
    @Bind(R.id.iv_bottom)
    ImageView ivBottom;

    private MagazineAdapter adapter;
    private Map<String, List> map;
    private List<String> keys;


    private String url = "http://mobile.iliangcang.com/topic/magazineList?app_key=Android&sig=8EB61B9784B7623223505352E626D648%7C974286010287453&v=1.0";


    @Override
    public View initView() {

        View view = View.inflate(context, R.layout.fragment_magazine, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initTitle() {
        super.initTitle();
        baseTitle.setText("杂志");
        tvJul.setVisibility(View.VISIBLE);
        ivBottom.setVisibility(View.VISIBLE);

    }

    @Override
    public void initData() {

        //头布局的点击监听
        rvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.showToast("此处弹出鱼尾纹");
            }
        });

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
                                Log.e("TAG", "MagazineFragment联网失败==" + e.getMessage());
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Log.e("TAG", "MagazineFragment联网成功==" + response);
                                //解析数据
                                parseData(response);

                                //设置适配器
                                adapter = new MagazineAdapter(context, map, keys, tvJul);
                                recyclerView.setAdapter(adapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                            }
                        });
            }
        });
    }


    //解析数据
    private void parseData(String json) {

        try {
            MagazineBean magazineBean = JSON.parseObject(json, MagazineBean.class);
            keys = magazineBean.getData().getItems().getKeys();

            List<Info> list = new ArrayList<>();

            map = new HashMap<>();

            if (keys != null && keys.size() > 0) {

                for (int i = 0; i < keys.size(); i++) {

                    JSONObject jsonObject = new JSONObject(json);
                    JSONObject jsonObject2 = jsonObject.optJSONObject("data");
                    JSONObject jsonObject3 = jsonObject2.optJSONObject("items");
                    JSONObject jsonobject4 = jsonObject3.optJSONObject("infos");

                    map.put(keys.get(i), list);

//                    Log.e("TAG", "Map==" + map.get(keys.get(i)));

                    JSONArray jsonArray = jsonobject4.optJSONArray(keys.get(i));

                    if (jsonArray != null && jsonArray.length() > 0) {

                        for (int j = 0; j < jsonArray.length(); j++) {


                            JSONObject object = (JSONObject) jsonArray.get(j);

                            if (object != null) {
//            "taid":"2062",
//            "topic_name":"他用一张196㎡的纸还原出世界上最后一头白犀牛",
//            "cat_id":"23",
//            "author_id":"1",
//            "topic_url":"http://www.iliangcang.com/i/topicapp/201707100852",
//            "access_url":"http://www.iliangcang.com/i/topicapp/201707100852",
//            "cover_img":"http://imgs-qn.iliangcang.com/ware/appimg/topic/cover/2062_.jpg?_t=1499788876",
//            "cover_img_new":"http://imgs-qn.iliangcang.com/ware/appimg/topic/cover/2062_.jpg?_t=1499788876",
//            "hit_number":452,
//            "addtime":"2017-07-12 18:09:31",
//            "content":"",
//            "nav_title":"良仓杂志",
//            "author_name":"良仓",
//            "cat_name":"视频"
                                Info info = new Info();
                                info.setTaid(object.optString("taid"));
                                info.setTopic_name(object.optString("topic_name"));
                                info.setCat_id(object.optString("cat_id"));
                                info.setAuthor_id(object.optString("author_id"));
                                info.setTopic_url(object.optString("topic_url"));
                                info.setAccess_url(object.optString("access_url"));
                                info.setCover_img(object.optString("cover_img"));
                                info.setCover_img_new(object.optString("cover_img_new"));
                                info.setHit_number(object.optInt("hit_number"));
                                info.setAddtime(object.optString("addtime"));
                                info.setContent(object.optString("content"));
                                info.setNav_title(object.optString("nav_title"));
                                info.setAuthor_name(object.optString("author_name"));
                                info.setCat_name(object.optString("cat_name"));

                                list.add(info);

//                                Log.e("TAg","list==="+list.size());  100个
                            }
                        }
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
