package com.atguigu.liangcang.shop.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.base_cart)
    ImageView baseCart;
    @Bind(R.id.base_title)
    TextView baseTitle;
    @Bind(R.id.rv_title)
    RelativeLayout rvTitle;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.ll_price)
    LinearLayout llPrice;


    private TextView tvAll;
    private TextView tv0200;
    private TextView tv201500;
    private TextView tv5011000;
    private TextView tv10013000;
    private TextView tv3000;
    private Button btn_kong;

    private String url;

    private DateilsAdapter adapter;

    private PopupWindow popupWindow;

    @Override
    public void initView() {
        url = getIntent().getStringExtra("url");
        showPopupWindow();

    }



    private void showPopupWindow() {
        View popupView = View.inflate(this, R.layout.pop_details, null);
        popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);

//        popupWindow.setTouchable(isshow);
//        popupWindow.setOutsideTouchable(isshow);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.i("mengdd", "onTouch : ");

                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x000000));


        UIUtils.setPopupWindowTouchModal(popupWindow, false);


        tvAll = (TextView) popupView.findViewById(R.id.tv_all);
        tv0200 = (TextView) popupView.findViewById(R.id.tv_0_200);
        tv201500 = (TextView) popupView.findViewById(R.id.tv_201_500);
        tv5011000 = (TextView) popupView.findViewById(R.id.tv_501_1000);
        tv10013000 = (TextView) popupView.findViewById(R.id.tv_1001_3000);
        tv3000 = (TextView) popupView.findViewById(R.id.tv_3000);
        btn_kong = (Button) popupView.findViewById(R.id.btn_kong);



    }

    @Override
    public void initData() {
        baseTitle.setText("商店");
        baseCart.setVisibility(View.VISIBLE);
        ivBack.setVisibility(View.VISIBLE);
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

//        Log.e("TAG","url=="+url);
    }

    //解析数据
    private void parseData(String json) {
        final DetailsBean detailsBean = new Gson().fromJson(json, DetailsBean.class);

//        Log.e("TAG", "name ==" + detailsBean.getData().getItems().get(0).getGoods_name());
        adapter = new DateilsAdapter(DetailsActivity.this, detailsBean.getData().getItems());
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                UIUtils.showToast(detailsBean.getData().getItems().get(position).getGoods_name());

                Intent intent = new Intent(DetailsActivity.this, PurchaseActivity.class);
                intent.putExtra("goods_id",detailsBean.getData().getItems().get(position).getGoods_id());
                startActivity(intent);
            }
        });

    }

    private boolean isshow = false;

    @Override
    public void initListener() {

        //设置返回按钮
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
            }
        });

        //设置点击弹出 popupWindow
        llPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isshow = !isshow;
                if (isshow) {
                    //相对某个控件的位置（正左下方），无偏移
                    popupWindow.showAsDropDown(llPrice);

                } else {
                    popupWindow.dismiss();
                    isshow = !isshow;

                }
            }
        });

        btn_kong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                isshow = !isshow;
                popupWindow.dismiss();

            }
        });


        tvAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvPrice.setText("价格筛选");
                tvAll.setBackgroundColor(Color.GRAY);
                tvAll.setTextColor(Color.WHITE);
                UIUtils.showToast("全部");

                tv0200.setBackgroundColor(Color.WHITE);
                tv0200.setTextColor(Color.BLACK);
                tv201500.setBackgroundColor(Color.WHITE);
                tv201500.setTextColor(Color.BLACK);
                tv5011000.setBackgroundColor(Color.WHITE);
                tv5011000.setTextColor(Color.BLACK);
                tv10013000.setBackgroundColor(Color.WHITE);
                tv10013000.setTextColor(Color.BLACK);
                tv3000.setBackgroundColor(Color.WHITE);
                tv3000.setTextColor(Color.BLACK);

//                isshow = !isshow;
                popupWindow.dismiss();
            }
        });
        tv0200.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvPrice.setText("0-200");
                tv0200.setBackgroundColor(Color.GRAY);
                tv0200.setTextColor(Color.WHITE);
                UIUtils.showToast("0-200");

                tvAll.setBackgroundColor(Color.WHITE);
                tvAll.setTextColor(Color.BLACK);
                tv201500.setBackgroundColor(Color.WHITE);
                tv201500.setTextColor(Color.BLACK);
                tv5011000.setBackgroundColor(Color.WHITE);
                tv5011000.setTextColor(Color.BLACK);
                tv10013000.setBackgroundColor(Color.WHITE);
                tv10013000.setTextColor(Color.BLACK);
                tv3000.setBackgroundColor(Color.WHITE);
                tv3000.setTextColor(Color.BLACK);

//                isshow = !isshow;
                popupWindow.dismiss();
            }
        });
        tv201500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvPrice.setText("201-500");
                tv201500.setBackgroundColor(Color.GRAY);
                tv201500.setTextColor(Color.WHITE);
                UIUtils.showToast("201-500");

                tv0200.setBackgroundColor(Color.WHITE);
                tv0200.setTextColor(Color.BLACK);
                tvAll.setBackgroundColor(Color.WHITE);
                tvAll.setTextColor(Color.BLACK);
                tv5011000.setBackgroundColor(Color.WHITE);
                tv5011000.setTextColor(Color.BLACK);
                tv10013000.setBackgroundColor(Color.WHITE);
                tv10013000.setTextColor(Color.BLACK);
                tv3000.setBackgroundColor(Color.WHITE);
                tv3000.setTextColor(Color.BLACK);

//                isshow = !isshow;
                popupWindow.dismiss();
            }
        });
        tv5011000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvPrice.setText("501-1000");
                tv5011000.setBackgroundColor(Color.GRAY);
                tv5011000.setTextColor(Color.WHITE);
                UIUtils.showToast("501-1000");

                tv0200.setBackgroundColor(Color.WHITE);
                tv0200.setTextColor(Color.BLACK);
                tv201500.setBackgroundColor(Color.WHITE);
                tv201500.setTextColor(Color.BLACK);
                tvAll.setBackgroundColor(Color.WHITE);
                tvAll.setTextColor(Color.BLACK);
                tv10013000.setBackgroundColor(Color.WHITE);
                tv10013000.setTextColor(Color.BLACK);
                tv3000.setBackgroundColor(Color.WHITE);
                tv3000.setTextColor(Color.BLACK);

//                isshow = !isshow;
                popupWindow.dismiss();
            }
        });
        tv10013000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvPrice.setText("1001-3000");
                tv10013000.setBackgroundColor(Color.GRAY);
                tv10013000.setTextColor(Color.WHITE);
                UIUtils.showToast("1001-3000");

                tv0200.setBackgroundColor(Color.WHITE);
                tv0200.setTextColor(Color.BLACK);
                tv201500.setBackgroundColor(Color.WHITE);
                tv201500.setTextColor(Color.BLACK);
                tv5011000.setBackgroundColor(Color.WHITE);
                tv5011000.setTextColor(Color.BLACK);
                tvAll.setBackgroundColor(Color.WHITE);
                tvAll.setTextColor(Color.BLACK);
                tv3000.setBackgroundColor(Color.WHITE);
                tv3000.setTextColor(Color.BLACK);

//                isshow = !isshow;
                popupWindow.dismiss();
            }
        });
        tv3000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvPrice.setText("3000以上");
                tv3000.setBackgroundColor(Color.GRAY);
                tv3000.setTextColor(Color.WHITE);
                UIUtils.showToast("3000以上");

                tv0200.setBackgroundColor(Color.WHITE);
                tv0200.setTextColor(Color.BLACK);
                tv201500.setBackgroundColor(Color.WHITE);
                tv201500.setTextColor(Color.BLACK);
                tv5011000.setBackgroundColor(Color.WHITE);
                tv5011000.setTextColor(Color.BLACK);
                tv10013000.setBackgroundColor(Color.WHITE);
                tv10013000.setTextColor(Color.BLACK);
                tvAll.setBackgroundColor(Color.WHITE);
                tvAll.setTextColor(Color.BLACK);

//                isshow = !isshow;
                popupWindow.dismiss();
            }
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                isshow = !isshow;
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
        return R.layout.activity_details;
    }


}
