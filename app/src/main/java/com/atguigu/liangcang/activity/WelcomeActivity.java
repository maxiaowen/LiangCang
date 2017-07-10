package com.atguigu.liangcang.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.atguigu.liangcang.R;
import com.atguigu.liangcang.base.BaseActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;

import butterknife.Bind;

public class WelcomeActivity extends BaseActivity {


    @Bind(R.id.iv_gif)
    ImageView ivGif;

    private long dur;
    private static final int MESSAGE_SUCCESS = 1;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){

                case MESSAGE_SUCCESS:

                    startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                    finish();
                    break;

            }
        }
    };

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

        Glide.with(this)
                .load(R.drawable.loading_start)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<Integer, GlideDrawable>() {

                    @Override
                    public boolean onException(Exception arg0, Integer arg1,
                                               Target<GlideDrawable> arg2, boolean arg3) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource,
                                                   Integer model, Target<GlideDrawable> target,
                                                   boolean isFromMemoryCache, boolean isFirstResource) {
                        // 计算动画时长
                        GifDrawable drawable = (GifDrawable) resource;
                        GifDecoder decoder = drawable.getDecoder();
                        for (int i = 0; i < drawable.getFrameCount(); i++) {
                            dur += decoder.getDelay(i);
                        }
                        //发送延时消息，通知动画结束
                        handler.sendEmptyMessageDelayed(MESSAGE_SUCCESS,dur);
                        return false;
                    }
                }) //仅仅加载一次gif动画
                .into(new GlideDrawableImageViewTarget(ivGif, 1));

    }

    @Override
    public void initView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

}



