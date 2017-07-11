package com.atguigu.liangcang.utils;

import android.content.Context;
import android.os.Process;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.atguigu.liangcang.common.MyApplication;

import java.lang.reflect.Method;
import java.util.Formatter;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/7/6.
 */

public class UIUtils {

    public static Context getContext(){
        return MyApplication.getContext();
    }


    /**
     * 开启一个线程池
     */
    private static ExecutorService service = Executors.newCachedThreadPool();

    public static ExecutorService getGlobalThread(){
        return service;
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


    /**
     * Set whether this window is touch modal or if outside touches will be sent
     * to
     * other windows behind it.
     *
     */
    public static void setPopupWindowTouchModal(PopupWindow popupWindow,
                                                boolean touchModal) {
        if (null == popupWindow) {
            return;
        }
        Method method;
        try {

            method = PopupWindow.class.getDeclaredMethod("setTouchModal",
                    boolean.class);
            method.setAccessible(true);
            method.invoke(popupWindow, touchModal);

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }



    private static StringBuilder mFormatBuilder = new StringBuilder();

    private static Formatter mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
    /**
     * �Ѻ���ת���ɣ�1:20:30������ʽ
     * @param timeMs
     * @return
     */
    public static String stringForTime(int timeMs) {
        int totalSeconds = timeMs / 1000;
        int seconds = totalSeconds % 60;

        int minutes = (totalSeconds / 60) % 60;

        int hours = totalSeconds / 3600;

        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds)
                    .toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

}
