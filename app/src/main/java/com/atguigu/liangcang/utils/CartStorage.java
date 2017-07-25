package com.atguigu.liangcang.utils;


import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;

import com.atguigu.liangcang.shop.bean.PurchaseBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/6/14.
 */


public class CartStorage {

    public static final String JSON_CART = "json_cart";
    public static CartStorage instance;
    private static Context mContext;
    /**
     * 数据存储在内存中
     */
    private SparseArray<PurchaseBean> sparseArray;

    private CartStorage() {
        //初始化集合
        sparseArray = new SparseArray<>();
        listTosparseArray();
    }

    private void listTosparseArray() {
        //得到所有数据
        ArrayList<PurchaseBean> datas = getAllData();
        for (int i = 0; i < datas.size(); i++) {
            PurchaseBean goodsBean = datas.get(i);
            sparseArray.put(Integer.parseInt(goodsBean.getData().getItems().getGoods_id()), goodsBean);

        }
    }

    /**
     * 得到所有数据
     *
     * @return
     */
    public ArrayList<PurchaseBean> getAllData() {
        return getLocalData();
    }

    /**
     * 得到本地缓存的数据
     *
     * @return
     */
    private ArrayList<PurchaseBean> getLocalData() {
        ArrayList<PurchaseBean> datas = new ArrayList<>();
        //json数据
        String saveJson = CacheUtils.getString(mContext, JSON_CART);
        if (!TextUtils.isEmpty(saveJson)) {
            //把保存的json数据解析成ArrayList数组
            datas = new Gson().fromJson(saveJson, new TypeToken<ArrayList<PurchaseBean>>() {
            }.getType());
        }
        return datas;
    }


    /**
     * 得到CartStorage
     *
     * @return
     */
    public static CartStorage getInstance(Context context) {
        if (instance == null) {
            mContext = context;
            //加上锁
            synchronized (CartStorage.class) {
                if (instance == null) {
                    instance = new CartStorage();
                }
            }
        }

        return instance;
    }

    /**
     * 添加数据
     *
     * @param bean
     */
    public void addData(PurchaseBean bean) {
        //查看内容中是否存在
        PurchaseBean temp = sparseArray.get(Integer.parseInt(bean.getData().getItems().getGoods_id()));
        if (temp != null) {
            //存在，就修改
//            temp.setNumber(bean.getNumber()+temp.getNumber());
            temp.getData().getItems().setNumber(bean.getData().getItems().getNumber()+temp.getData().getItems().getNumber());
        } else {
            //如果不存在，保存到内存中
            temp = bean;
        }

        //内存中更新
        sparseArray.put(Integer.parseInt(temp.getData().getItems().getGoods_id()), temp);


        //同步到本地
        commit();


    }


    /**
     * 删除数据
     *
     * @param bean
     */
    public void deleteData(PurchaseBean bean) {
        //内存中更新
        sparseArray.delete(Integer.parseInt(bean.getData().getItems().getGoods_id()));
        //同步到本地
        commit();

    }


    /**
     * 修改数据
     *
     * @param bean
     */
    public void updateData(PurchaseBean bean) {
        //内存中更新
        sparseArray.put(Integer.parseInt(bean.getData().getItems().getGoods_id()), bean);
        //同步到本地
        commit();

    }

    /**
     * 在本地保存一份
     */
    private void commit() {
        //把SparseArray 转换成List集合
        ArrayList<PurchaseBean> goodsBeens = sparseArrayToList();
        //使用Gson把List集合转换成json的String数据
        String json = new Gson().toJson(goodsBeens);
        //把文本保存到sp中
        CacheUtils.putString(mContext, JSON_CART, json);


    }

    private ArrayList<PurchaseBean> sparseArrayToList() {
        ArrayList<PurchaseBean> goodsBeens = new ArrayList<>();
        for (int i = 0; i < sparseArray.size(); i++) {
            PurchaseBean goodsBean = sparseArray.valueAt(i);
            goodsBeens.add(goodsBean);
        }
        return goodsBeens;
    }


//    public GoodsBean findData(int id) {
//        GoodsBean goodsBean = sparseArray.get(id);
//
//
//        GoodsBean newgoodsBean = new GoodsBean();
//        newgoodsBean.setCheck(goodsBean.isCheck());
//        newgoodsBean.setNumber(goodsBean.getNumber());
//        newgoodsBean.setFigure(goodsBean.getFigure());
//        newgoodsBean.setProduct_id(goodsBean.getProduct_id());
//        newgoodsBean.setCover_price(goodsBean.getCover_price());
//        newgoodsBean.setName(goodsBean.getName());
//
//        return newgoodsBean;
//    }
}

