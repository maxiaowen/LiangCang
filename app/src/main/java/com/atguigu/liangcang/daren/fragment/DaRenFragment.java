package com.atguigu.liangcang.daren.fragment;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.atguigu.liangcang.R;
import com.atguigu.liangcang.base.BaseFragment;
import com.atguigu.liangcang.daren.adapter.DaRenAdapter;
import com.atguigu.liangcang.daren.bean.DaRenBean;
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

public class DaRenFragment extends BaseFragment {
    @Bind(R.id.rv_daren)
    RecyclerView rvDaren;
    @Bind(R.id.base_select)
    ImageView baseSelect;
    @Bind(R.id.base_close)
    ImageView baseClose;
    @Bind(R.id.base_title)
    TextView baseTitle;
    @Bind(R.id.base_search)
    ImageView baseSearch;


    RadioButton rbDefault;
    RadioButton rbMost;
    RadioButton rbWelcome;
    RadioButton rbNew;
    RadioButton rbJoin;
    Button btnKong;

    private DaRenAdapter adapter;

    private String url1 = "http://mobile.iliangcang.com/user/masterList?app_key=Android&count=18&page=1&sig=79F01B94B8EBEFAC8EEB344EE2B20AA2%7C383889010803768&v=1.0";

    private PopupWindow popupWindow;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_daren, null);
        ButterKnife.bind(this, view);
        showPopupWindow();

        return view;
    }

    @Override
    public void initTitle() {
        super.initTitle();
        baseSearch.setVisibility(View.VISIBLE);
        baseSelect.setVisibility(View.VISIBLE);
        baseTitle.setText("达人");
    }

    @Override
    public void initData() {
        //连网请求数据
        netWorking(url1);

    }

    private void netWorking(final String url) {
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
    }

    //解析数据
    private void parseData(String json) {
        final DaRenBean daRenBean = new Gson().fromJson(json, DaRenBean.class);

        Log.e("TAG", "name ==" + daRenBean.getData().getItems().get(0).getUsername());
        adapter = new DaRenAdapter(context, daRenBean.getData().getItems());
        rvDaren.setAdapter(adapter);

        rvDaren.setLayoutManager(new GridLayoutManager(context, 3));


        adapter.setOnItemClickListener(new DaRenAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                UIUtils.showToast(daRenBean.getData().getItems().get(position).getNickname());
            }
        });


    }


    @Override
    public void initListener() {
        super.initListener();

        baseSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //相对某个控件的位置（正左下方），无偏移
                popupWindow.showAsDropDown(baseTitle);

                baseSelect.setVisibility(View.GONE);
                baseClose.setVisibility(View.VISIBLE);
            }
        });


        baseClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                baseSelect.setVisibility(View.VISIBLE);
                baseClose.setVisibility(View.GONE);

            }
        });
    }

    private void showPopupWindow() {
        View inflate = View.inflate(context, R.layout.popup_window, null);
        popupWindow = new PopupWindow(inflate, GridLayout.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.MATCH_PARENT,true);
//        popupWindow.setTouchable(true);
//        popupWindow.setOutsideTouchable(true);
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



        rbDefault = (RadioButton) inflate.findViewById(R.id.rb_default);
        rbMost = (RadioButton) inflate.findViewById(R.id.rb_most);
        rbWelcome = (RadioButton) inflate.findViewById(R.id.rb_welcome);
        rbNew = (RadioButton) inflate.findViewById(R.id.rb_new);
        rbJoin = (RadioButton) inflate.findViewById(R.id.rb_join);
        btnKong = (Button) inflate.findViewById(R.id.btn_kong);


        rbDefault.setOnClickListener(new MyOnClickListener());
        rbMost.setOnClickListener(new MyOnClickListener( ));
        rbWelcome.setOnClickListener(new MyOnClickListener());
        rbNew.setOnClickListener(new MyOnClickListener());
        rbJoin.setOnClickListener(new MyOnClickListener());
        btnKong.setOnClickListener(new MyOnClickListener());

    }

    class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rb_default:

                    String url = "http://mobile.iliangcang.com/user/masterList?app_key=Android&count=18&page=1&sig=79F01B94B8EBEFAC8EEB344EE2B20AA2%7C383889010803768&v=1.0";
                    netWorking(url);
                    popupWindow.dismiss();
                    baseSelect.setVisibility(View.VISIBLE);
                    baseClose.setVisibility(View.GONE);
                    break;
                case R.id.rb_most:
                    url = "http://mobile.iliangcang.com/user/masterList?app_key=Android&count=18&orderby=goods_sum&page=1&sig=05D2057FE3D726A43A94505807516FC3%7C136072130089168&v=1.0";
                    netWorking(url);
                    popupWindow.dismiss();
                    baseSelect.setVisibility(View.VISIBLE);
                    baseClose.setVisibility(View.GONE);
                    break;
                case R.id.rb_welcome:
                    url = "http://mobile.iliangcang.com/user/masterList?app_key=Android&count=18&orderby=followers&page=9&sig=05D2057FE3D726A43A94505807516FC3|136072130089168&v=1.0";
                    netWorking(url);
                    popupWindow.dismiss();
                    baseSelect.setVisibility(View.VISIBLE);
                    baseClose.setVisibility(View.GONE);
                    break;
                case R.id.rb_new:
                    url = "http://mobile.iliangcang.com/user/masterList?app_key=Android&count=18&orderby=reg_time&page=9&sig=05D2057FE3D726A43A94505807516FC3|136072130089168&v=1.0";
                    netWorking(url);
                    popupWindow.dismiss();
                    baseSelect.setVisibility(View.VISIBLE);
                    baseClose.setVisibility(View.GONE);
                    break;
                case R.id.rb_join:
                    url = "http://mobile.iliangcang.com/user/masterList?app_key=Android&count=18&orderby=action_time&page=9&sig=05D2057FE3D726A43A94505807516FC3|136072130089168&v=1.0";
                    netWorking(url);
                    popupWindow.dismiss();
                    baseSelect.setVisibility(View.VISIBLE);
                    baseClose.setVisibility(View.GONE);
                    break;
                case R.id.btn_kong:
                    popupWindow.dismiss();
                    baseSelect.setVisibility(View.VISIBLE);
                    baseClose.setVisibility(View.GONE);
                    break;
            }
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }



}
