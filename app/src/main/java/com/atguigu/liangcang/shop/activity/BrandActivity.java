package com.atguigu.liangcang.shop.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atguigu.liangcang.R;
import com.atguigu.liangcang.base.BaseActivity;
import com.atguigu.liangcang.shop.bean.ProductBean;
import com.atguigu.liangcang.shop.fragment.ProductFragment;
import com.atguigu.liangcang.shop.fragment.StoryFragment;
import com.atguigu.liangcang.utils.UIUtils;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import okhttp3.Call;

public class BrandActivity extends BaseActivity {


    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.base_title)
    TextView baseTitle;
    @Bind(R.id.iv_titie)
    ImageView ivTitie;
    @Bind(R.id.tv_brand_name)
    TextView tvBrandName;
    @Bind(R.id.rb_story)
    RadioButton rbStory;
    @Bind(R.id.rb_product)
    RadioButton rbProduct;
    @Bind(R.id.rg_brand)
    RadioGroup rgBrand;
    @Bind(R.id.fl_main)
    FrameLayout flMain;
    @Bind(R.id.activity_brand)
    RelativeLayout activityBrand;

    private String url;

    private Bundle bundle;
    private ProductBean productBean;
    private StoryFragment storyFragment;
    private ProductFragment productFragment;


    @Override
    public void initView() {

        String brand_id = getIntent().getStringExtra("brand_id");
//        Log.e("TAG","brand_id==="+brand_id);
        url = "http://mobile.iliangcang.com/brand/brandShopList?app_key=Android&brand_id=" + brand_id + "&count=20&page=1&sig=430BD99E6C913B8B8C3ED109737ECF15%7C830952120106768&v=1.0";
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
                                Log.e("TAG", "BrandActivity---联网成功==" + response);
                                //解析数据
                                parseData(response);
                            }
                        });
            }
        });
    }

    //解析数据
    private void parseData(String json) {
        productBean = new Gson().fromJson(json, ProductBean.class);
        Log.e("TAG", "name ==" + productBean.getData().getItems().get(0).getBrand_info().getBrand_desc());
        Picasso.with(this)
                .load(productBean.getData().getItems().get(0).getBrand_info().getBrand_logo())
                .placeholder(R.drawable.atguigu_logo)
                .error(R.drawable.atguigu_logo)
                .into(ivTitie);

        tvBrandName.setText(productBean.getData().getItems().get(0).getBrand_info().getBrand_name());

        switchFrgment(R.id.rb_product);

    }


    @Override
    public void initListener() {

        //radioGroup监听
        rgBrand.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switchFrgment(checkedId);
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
            }
        });

    }

    private void switchFrgment(int checkedId) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        hidden(ft);

        switch (checkedId) {
            case R.id.rb_story:
                if (storyFragment == null) {
                    storyFragment = new StoryFragment();
                    bundle = new Bundle();
                    bundle.putSerializable("productBean", productBean);
                    storyFragment.setArguments(bundle);
                    ft.add(R.id.fl_main, storyFragment);
                } else {
                    ft.show(storyFragment);
                }

                rbStory.setTextColor(Color.BLACK);
                rbStory.setBackgroundColor(Color.WHITE);
                rbProduct.setTextColor(Color.WHITE);
                rbProduct.setBackgroundColor(Color.parseColor("#555555"));
                break;

            case R.id.rb_product:
                if (productFragment == null) {
                    productFragment = new ProductFragment();
                    bundle = new Bundle();
                    bundle.putSerializable("productBean", productBean);
                    productFragment.setArguments(bundle);
                    ft.add(R.id.fl_main, productFragment);
                } else {
                    ft.show(productFragment);
                }

                rbStory.setTextColor(Color.WHITE);
                rbStory.setBackgroundColor(Color.parseColor("#555555"));
                rbProduct.setTextColor(Color.BLACK);
                rbProduct.setBackgroundColor(Color.WHITE);
                break;
        }

        ft.commit();
    }

    /**
     * 隐藏Fragment
     *
     * @param ft
     */
    private void hidden(FragmentTransaction ft) {
        if (storyFragment != null) {
            ft.hide(storyFragment);
        }
        if (productFragment != null) {
            ft.hide(productFragment);
        }
    }


    @Override
    public void initTitle() {
        super.initTitle();
        baseTitle.setText("品牌产品");
        ivBack.setVisibility(View.VISIBLE);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_brand;
    }


}
