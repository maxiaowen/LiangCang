package com.atguigu.liangcang.shop.activity;

import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atguigu.liangcang.R;
import com.atguigu.liangcang.base.BaseActivity;

import butterknife.Bind;

public class ShoppingcartActivity extends BaseActivity {


    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_compile)
    TextView tvCompile;
    @Bind(R.id.base_title)
    TextView baseTitle;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.checkbox_all)
    CheckBox checkboxAll;
    @Bind(R.id.tv_shopcart_total)
    TextView tvShopcartTotal;
    @Bind(R.id.btn_check_out)
    Button btnCheckOut;
    @Bind(R.id.ll_check_all)
    LinearLayout llCheckAll;

    @Override
    public void initTitle() {
        super.initTitle();

        baseTitle.setText("购物车");
        ivBack.setVisibility(View.VISIBLE);
        tvCompile.setVisibility(View.VISIBLE);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
            }
        });
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
        return R.layout.activity_shoppingcart;
    }


}
