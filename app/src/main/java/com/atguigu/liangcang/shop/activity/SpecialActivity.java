package com.atguigu.liangcang.shop.activity;

import android.os.Build;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.atguigu.liangcang.R;
import com.atguigu.liangcang.base.BaseActivity;

import butterknife.Bind;

public class SpecialActivity extends BaseActivity {


    @Bind(R.id.webView)
    WebView webView;

    private String url;

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

        //设置webView的数据
        setWebViewData();
    }

    private void setWebViewData() {
        WebSettings webSettings = webView.getSettings();
        //设置支持js
        webSettings.setJavaScriptEnabled(true);
        //设置支持双击变大变小
        webSettings.setUseWideViewPort(true);

        //设置检索缓存的
        webSettings.setCacheMode(webSettings.LOAD_CACHE_ELSE_NETWORK);

        //设置不跳转到系统的浏览器
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                }
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

        webView.loadUrl(url);
    }

    @Override
    public void initView() {
        url = getIntent().getStringExtra("url");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_special;
    }


}
