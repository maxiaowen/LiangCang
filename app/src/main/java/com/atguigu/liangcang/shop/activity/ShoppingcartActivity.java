package com.atguigu.liangcang.shop.activity;

import android.support.v7.widget.LinearLayoutManager;
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
import com.atguigu.liangcang.shop.adapter.ShoppingcartAdapter;
import com.atguigu.liangcang.shop.bean.PurchaseBean;
import com.atguigu.liangcang.utils.CartStorage;

import java.util.List;

import butterknife.Bind;

import static com.atguigu.liangcang.R.id.tv_compile;

public class ShoppingcartActivity extends BaseActivity {


    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(tv_compile)
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


    private ShoppingcartAdapter adapter;
    private List<PurchaseBean> datas;


    @Override
    public void initTitle() {
        super.initTitle();

        baseTitle.setText("购物车");
        ivBack.setVisibility(View.VISIBLE);
        tvCompile.setVisibility(View.VISIBLE);


    }

    @Override
    public void initView() {
        datas = CartStorage.getInstance(this).getAllData();
        adapter = new ShoppingcartAdapter(this, datas, checkboxAll, tvShopcartTotal);
    }

    @Override
    public void initData() {
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void initListener() {

        //设置点击返回按钮
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
            }
        });

        checkboxAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean checked = checkboxAll.isChecked();
                //设置是否选择
                adapter.checkAll_none(checked);

                //重新计算价格
                adapter.showTotalPrice();
            }
        });


        tvCompile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = tvCompile.getText().toString().trim();
                if (str.equals("编辑")) {
                    tvCompile.setText("完成");
                    adapter.showDelete(true);
                }

                if (str.equals("完成")) {
                    tvCompile.setText("编辑");
                    adapter.showDelete(false);
                }
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
