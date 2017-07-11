package com.atguigu.liangcang.common;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import org.xutils.BuildConfig;
import org.xutils.x;


/**
 * Created by Administrator on 2017/7/6.
 */

public class MyApplication extends Application {

    private static Context context;
    private static Handler handler;
    private static int pid;

    public static Context getContext() {
        return context;
    }

    public static Handler getHandler() {
        return handler;
    }

    public static int getPid() {
        return pid;
    }

    @Override
    public void onCreate() {
        super.onCreate();

//        xUtils初始化
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.

        context = this;
        handler = new Handler();
        pid = android.os.Process.myPid();
    }
}
