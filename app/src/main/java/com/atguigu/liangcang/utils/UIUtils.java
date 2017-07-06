package com.atguigu.liangcang.utils;

import android.content.Context;
import android.os.Process;
import android.widget.Toast;

import com.atguigu.liangcang.common.MyApplication;

/**
 * Created by Administrator on 2017/7/6.
 */

public class UIUtils {

    public static Context getContext(){
        return MyApplication.getContext();
    }


    /**
     * 保证在主线程执行
     */
    public static void UIThread(Runnable runnable){
        if(MyApplication.getPid() == Process.myTid()) {
            runnable.run();
        }else {
            MyApplication.getHandler().post(runnable);
        }
    }

    /**
     * 显示吐司
     */
    public static void showToast(final String messager){
        UIThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), messager, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
