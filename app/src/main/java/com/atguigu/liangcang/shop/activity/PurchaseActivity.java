package com.atguigu.liangcang.shop.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.atguigu.liangcang.R;
import com.atguigu.liangcang.base.BaseActivity;
import com.atguigu.liangcang.base.BaseFragment;
import com.atguigu.liangcang.common.MyApplication;
import com.atguigu.liangcang.shop.bean.PurchaseBean;
import com.atguigu.liangcang.shop.customview.AddSubView;
import com.atguigu.liangcang.shop.fragment.DetailsFragment;
import com.atguigu.liangcang.shop.fragment.MallFragment;
import com.atguigu.liangcang.utils.CartStorage;
import com.atguigu.liangcang.utils.GlideImageLoader;
import com.atguigu.liangcang.utils.UIUtils;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

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
    @Bind(R.id.view_xian)
    View viewXian;

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


    private PurchaseBean purchaseBean;


    private DetailsFragment detailsFragment;
    private MallFragment mallFragment;
    private Bundle bundle;

    private PopupWindow popupWindow;
    private LinearLayout ll_layout;
    private TextView tv_pop_name;
    private TextView tv_pop_content;
    private TextView tv_pop_price;
    private ImageView iv_chahao;
    private ImageView iv_tupian;
    private Button btn_queren;
    private LinearLayout ll_aaa;
    private Button btn_join;
    private Button btn_costco;
    private AddSubView addSubView;
    private TextView tv_type_name1;
    private TextView tv_type_name2;
    private TagFlowLayout flowlayout1;
    private TagFlowLayout flowlayout2;

    @Override
    public void initView() {
        goods_id = getIntent().getStringExtra("goods_id");
        url = "http://mobile.iliangcang.com/goods/goodsDetail?app_key=Android&goods_id=" + goods_id + "&sig=430BD99E6C913B8B8C3ED109737ECF15%7C830952120106768&v=1.0";

        Log.e("TAG", "PurchaseActivity-------url==" + url);

        // 初始化Fragment
        initFragment();

        //初始化PopupWindow
        showPopupWindow();

    }

    private void showPopupWindow() {
        View popupView = View.inflate(this, R.layout.pop_purchase, null);
        popupWindow = new PopupWindow(popupView, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT, true);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x000000));
        UIUtils.setPopupWindowTouchModal(popupWindow, false);

        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);

        ll_layout = (LinearLayout) popupView.findViewById(R.id.ll_layout);
        tv_pop_name = (TextView) popupView.findViewById(R.id.tv_pop_name);
        tv_pop_content = (TextView) popupView.findViewById(R.id.tv_pop_content);
        tv_pop_price = (TextView) popupView.findViewById(R.id.tv_pop_price);
        iv_chahao = (ImageView) popupView.findViewById(R.id.iv_chahao);
        iv_tupian = (ImageView) popupView.findViewById(R.id.iv_tupian);
        btn_queren = (Button) popupView.findViewById(R.id.btn_queren);
        ll_aaa = (LinearLayout) popupView.findViewById(R.id.ll_aaa);
        btn_join = (Button) popupView.findViewById(R.id.btn_join);
        btn_costco = (Button) popupView.findViewById(R.id.btn_costco);
        addSubView = (AddSubView) popupView.findViewById(R.id.addSubView);
        flowlayout1 = (TagFlowLayout) popupView.findViewById(R.id.flowlayout1);
        flowlayout2 = (TagFlowLayout) popupView.findViewById(R.id.flowlayout2);
        tv_type_name1 = (TextView) popupView.findViewById(R.id.tv_type_name1);
        tv_type_name2 = (TextView) popupView.findViewById(R.id.tv_type_name2);


    }

    // 初始化Fragment
    private void initFragment() {
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

        purchaseBean = JSON.parseObject(json, PurchaseBean.class);

//        Log.e("TAG", "PurchaseActivity--purchaseBean==" + purchaseBean.toString());

        //设置数据
        setData();

        //设置PopupWindow中的数据
        setPopupWindowData();


    }

    private void setData() {

        //设置Banner 数据
        List<String> images = new ArrayList<>();
        List<String> images_item = purchaseBean.getData().getItems().getImages_item();
        for (int i = 0; i < images_item.size(); i++) {
            images.add(images_item.get(i));
        }
        banner.setImages(images)
                .setImageLoader(new GlideImageLoader())
                .start();

        tvName.setText(purchaseBean.getData().getItems().getOwner_name());

        tvCount.setText(purchaseBean.getData().getItems().getLike_count());

        tvGoodsName.setText(purchaseBean.getData().getItems().getGoods_name());

        tvDiscountPrice.setText("￥" + purchaseBean.getData().getItems().getPrice());


        tvBiao.setText(purchaseBean.getData().getItems().getBrand_info().getBrand_name());

        Picasso.with(this)
                .load(purchaseBean.getData().getItems().getBrand_info().getBrand_logo())
                .placeholder(R.drawable.atguigu_logo)
                .error(R.drawable.atguigu_logo)
                .into(ivBiao);


        bundle.putSerializable("purchaseBean", purchaseBean);
        bundle.putString("goods_id", goods_id);

        //默认选中首页-放在setOnCheckedChangeListener 执行之后
        rgButton.check(R.id.rb_default);


    }

    //设置PopupWindow中的数据
    private void setPopupWindowData() {

        tv_pop_name.setText(purchaseBean.getData().getItems().getOwner_name());
        tv_pop_content.setText(purchaseBean.getData().getItems().getGoods_name());
        tv_pop_price.setText(purchaseBean.getData().getItems().getPrice());
        Picasso.with(this)
                .load(purchaseBean.getData().getItems().getGoods_image())
                .placeholder(R.drawable.atguigu_logo)
                .error(R.drawable.atguigu_logo)
                .into(iv_tupian);
        addSubView.setMaxvalue(Integer.parseInt(purchaseBean.getData().getItems().getSku_inv().get(0).getAmount()));

        addSubView.setOnNumberChangeListener(new AddSubView.OnNumberChangeListener() {
            @Override
            public void numberChange(int value) {
                //把Bean对象更新一下
                purchaseBean.getData().getItems().setNumber(value);
                //更新存储到本地或者服务器上
//                CartStorage.getInstance(MyApplication.getContext()).updateData(purchaseBean);

            }

        });

        List<PurchaseBean.DataBean.ItemsBean.SkuInfoBean> sku_info = purchaseBean.getData().getItems().getSku_info();
        if (sku_info.size() == 1) {

            setInfo1(sku_info);
        } else {

            setInfo1(sku_info);
            setInfo2(sku_info);
        }
    }


    private void setInfo1(List<PurchaseBean.DataBean.ItemsBean.SkuInfoBean> sku_info) {
        tv_type_name1.setText(sku_info.get(0).getType_name());

        final String[] mVals = new String[sku_info.get(0).getAttrList().size()];
        final String[] urls = new String[sku_info.get(0).getAttrList().size()];

        for (int i = 0; i < sku_info.get(0).getAttrList().size(); i++) {
            mVals[i] = sku_info.get(0).getAttrList().get(i).getAttr_name();
            urls[i] = sku_info.get(0).getAttrList().get(i).getImg_path();

        }


        final LayoutInflater mInflater = LayoutInflater.from(this);
        flowlayout1.setAdapter(new TagAdapter<String>(mVals) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.tv, flowlayout1, false);
                tv.setText(s);
                return tv;
            }

            @Override
            public boolean setSelected(int position, String s) {
                return s.equals(mVals[0]);
            }
        });


        flowlayout1.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
//                UIUtils.showToast(mVals[position]);
                if (!urls[position].equals("")) {
                    Picasso.with(PurchaseActivity.this)
                            .load(urls[position])
                            .placeholder(R.drawable.atguigu_logo)
                            .error(R.drawable.atguigu_logo)
                            .into(iv_tupian);
                }
                return true;
            }
        });


    }


    private void setInfo2(List<PurchaseBean.DataBean.ItemsBean.SkuInfoBean> sku_info) {
        tv_type_name2.setVisibility(View.VISIBLE);
        tv_type_name2.setText(sku_info.get(1).getType_name());
        flowlayout2.setVisibility(View.VISIBLE);


        final String[] mVals = new String[sku_info.get(1).getAttrList().size()];
        final String[] urls = new String[sku_info.get(1).getAttrList().size()];

        for (int i = 0; i < sku_info.get(1).getAttrList().size(); i++) {
            mVals[i] = sku_info.get(1).getAttrList().get(i).getAttr_name();
            urls[i] = sku_info.get(1).getAttrList().get(i).getImg_path();

        }


        final LayoutInflater mInflater = LayoutInflater.from(this);
        flowlayout2.setAdapter(new TagAdapter<String>(mVals) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.tv, flowlayout2, false);
                tv.setText(s);
                return tv;
            }

            //默认选中哪一条
            @Override
            public boolean setSelected(int position, String s) {
                return s.equals(mVals[0]);
            }
        });


        flowlayout2.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
//                UIUtils.showToast(mVals[position]);
//                if(!urls[position].equals("")) {
//                    Picasso.with(PurchaseActivity.this)
//                            .load(urls[position])
//                            .placeholder(R.drawable.atguigu_logo)
//                            .error(R.drawable.atguigu_logo)
//                            .into(iv_tupian);
//                }
                return true;
            }
        });
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


        //点击进入购物车
        baseCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PurchaseActivity.this, ShoppingcartActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });


        llDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PurchaseActivity.this, BrandActivity.class);
                intent.putExtra("brand_id", purchaseBean.getData().getItems().getBrand_info().getBrand_id());
                startActivity(intent);
            }
        });

        //设置弹出popupWindow
        llSelecet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //相对某个控件的位置（正左下方），无偏移
                popupWindow.showAsDropDown(viewXian);
                ll_aaa.setVisibility(View.VISIBLE);
                btn_queren.setVisibility(View.GONE);
            }
        });


        //设置弹出popupWindow
        tvJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //相对某个控件的位置（正左下方），无偏移
                popupWindow.showAsDropDown(viewXian);
                btn_queren.setVisibility(View.VISIBLE);
                ll_aaa.setVisibility(View.GONE);
            }
        });

        //设置弹出popupWindow
        tvCostco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //相对某个控件的位置（正左下方），无偏移
                popupWindow.showAsDropDown(viewXian);
                btn_queren.setVisibility(View.VISIBLE);
                ll_aaa.setVisibility(View.GONE);
            }
        });

        //关闭popupWindow
        iv_chahao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });


        //设置PopupWindow中的点击监听

        //商品添加到购物车
        btn_queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                //添加购物车
                CartStorage.getInstance(MyApplication.getContext()).addData(purchaseBean);
                UIUtils.showToast("添加购物车成功");
            }
        });

        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                //添加购物车
                CartStorage.getInstance(MyApplication.getContext()).addData(purchaseBean);
                UIUtils.showToast("添加购物车成功");
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
