package com.atguigu.liangcang.shop.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.atguigu.liangcang.R;
import com.atguigu.liangcang.base.BaseActivity;
import com.atguigu.liangcang.base.BaseFragment;
import com.atguigu.liangcang.shop.bean.PurchaseBean;
import com.atguigu.liangcang.shop.fragment.DetailsFragment;
import com.atguigu.liangcang.shop.fragment.MallFragment;
import com.atguigu.liangcang.utils.GlideImageLoader;
import com.atguigu.liangcang.utils.UIUtils;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import okhttp3.Call;

public class PurchaseActivity extends BaseActivity {

    @Bind(R.id.banner)
    Banner banner;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_count)
    TextView tvCount;
    @Bind(R.id.tv_goods_name)
    TextView tvGoodsName;
    @Bind(R.id.iv_share)
    ImageView ivShare;
    @Bind(R.id.tv_discount_price)
    TextView tvDiscountPrice;
    @Bind(R.id.ll_selecet)
    LinearLayout llSelecet;
    @Bind(R.id.ll_details)
    LinearLayout llDetails;
    @Bind(R.id.rb_default)
    RadioButton rbDefault;
    @Bind(R.id.rb_shop)
    RadioButton rbShop;
    @Bind(R.id.rg_button)
    RadioGroup rgButton;
    @Bind(R.id.frame_layout)
    FrameLayout frameLayout;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.base_cart)
    ImageView baseCart;
    @Bind(R.id.iv_cus)
    ImageView ivCus;
    @Bind(R.id.tv_join)
    TextView tvJoin;
    @Bind(R.id.tv_costco)
    TextView tvCostco;
    @Bind(R.id.activity_purchase)
    RelativeLayout activityPurchase;
    @Bind(R.id.iv_biao)
    ImageView ivBiao;
    @Bind(R.id.tv_biao)
    TextView tvBiao;

    private String url;

    private String goods_id;

    /**
     * 装各个Fragment的集合
     */
    private ArrayList<BaseFragment> fragments;
    /**
     * 之前显示的Fragment
     */
    private Fragment tempFragment;

    /**
     * 被选中页面的位置
     */
    private int position;

    //开启事务
    private FragmentTransaction ft;


    private DetailsFragment detailsFragment;
    private MallFragment mallFragment;
    private Bundle bundle;


    @Override
    public void initView() {
        goods_id = getIntent().getStringExtra("goods_id");
        url = "http://mobile.iliangcang.com/goods/goodsDetail?app_key=Android&goods_id=" + goods_id + "&sig=430BD99E6C913B8B8C3ED109737ECF15%7C830952120106768&v=1.0";

        Log.e("TAG", "PurchaseActivity-------url==" + url);

        // 初始化Fragment
        fragments = new ArrayList<>();
        detailsFragment = new DetailsFragment();
        mallFragment = new MallFragment();
        bundle = new Bundle();

        detailsFragment.setArguments(bundle);
        mallFragment.setArguments(bundle);
        //如果transaction  commit（）过  那么我们要重新得到transaction
        ft = getSupportFragmentManager().beginTransaction();
        ft.commit();

        fragments.add(detailsFragment);
        fragments.add(mallFragment);

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
                                Log.e("TAG", "PurchaseActivity--联网失败==" + e.getMessage());
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Log.e("TAG", "PurchaseActivity--联网成功==" + response);
                                //解析数据
                                parseData(response);
                            }
                        });
            }
        });
    }

    //解析数据
    private void parseData(String json) {

        PurchaseBean purchaseBean = JSON.parseObject(json, PurchaseBean.class);

//        Log.e("TAG", "PurchaseActivity--purchaseBean==" + purchaseBean.toString());

        //设置数据
        setData(purchaseBean);


    }

    private void setData(PurchaseBean data) {

        //设置Banner 数据
        List<String> images = new ArrayList<>();
        List<String> images_item = data.getData().getItems().getImages_item();
        for (int i = 0; i < images_item.size(); i++) {
            images.add(images_item.get(i));
        }
        banner.setImages(images)
                .setImageLoader(new GlideImageLoader())
                .start();

        tvName.setText(data.getData().getItems().getOwner_name());

        tvCount.setText(data.getData().getItems().getLike_count());

        tvGoodsName.setText(data.getData().getItems().getGoods_name());

        tvDiscountPrice.setText("￥" + data.getData().getItems().getPrice());


        tvBiao.setText(data.getData().getItems().getBrand_info().getBrand_name());

        Picasso.with(this)
                .load(data.getData().getItems().getBrand_info().getBrand_logo())
                .placeholder(R.drawable.atguigu_logo)
                .error(R.drawable.atguigu_logo)
                .into(ivBiao);


        bundle.putSerializable("purchaseBean", data);
        bundle.putString("goods_id",goods_id);

        //默认选中首页-放在setOnCheckedChangeListener 执行之后
        rgButton.check(R.id.rb_default);

    }

    @Override
    public void initListener() {

        /**
         * 监听RadioGroup专题的变化
         */
        rgButton.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_default://商品详情
                        position = 0;
                        rbDefault.setTextColor(Color.BLACK);
                        rbDefault.setBackgroundColor(Color.WHITE);

                        rbShop.setTextColor(Color.WHITE);
                        rbShop.setBackgroundColor(Color.BLACK);
                        break;
                    case R.id.rb_shop://购物须知
                        position = 1;
                        rbShop.setTextColor(Color.BLACK);
                        rbShop.setBackgroundColor(Color.WHITE);

                        rbDefault.setTextColor(Color.WHITE);
                        rbDefault.setBackgroundColor(Color.BLACK);
                        break;

                }

                Fragment currentFragment = getFragment(position);//当前
                switchFragment(currentFragment);
            }
        });

        //设置返回按钮的点击事件
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
            }
        });

    }

    /**
     * 传入当前要显示的Fragment
     *
     * @param currentFragment
     */
    private void switchFragment(Fragment currentFragment) {
        if (tempFragment != currentFragment) {

            if (currentFragment != null) {
                //开启事务
                ft = getSupportFragmentManager().beginTransaction();
                if (!currentFragment.isAdded()) {
                    //隐藏之前显示的
                    if (tempFragment != null) {
                        ft.hide(tempFragment);
                    }
                    //判断currentFragment 有没有添加，如果没有就添加
                    ft.add(R.id.frame_layout, currentFragment);


                } else {
                    //隐藏之前显示的
                    if (tempFragment != null) {
                        ft.hide(tempFragment);
                    }
                    //否则就显示
                    ft.show(currentFragment);
                }

                ft.commit();//统一提交

            }

            tempFragment = currentFragment;
        }
    }

    /**
     * 根据位置得到Fragment
     *
     * @param position
     * @return
     */
    private Fragment getFragment(int position) {
        if (fragments != null && fragments.size() > 0) {
            return fragments.get(position);
        }
        return null;
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
        return R.layout.activity_purchase;
    }


}
