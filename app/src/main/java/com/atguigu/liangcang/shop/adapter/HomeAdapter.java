package com.atguigu.liangcang.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.atguigu.liangcang.R;
import com.atguigu.liangcang.shop.bean.HomeBean;
import com.atguigu.liangcang.utils.GlideImageLoader;
import com.atguigu.liangcang.utils.UIUtils;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.R.attr.type;

/**
 * Created by Administrator on 2017/7/11.
 */

public class HomeAdapter extends RecyclerView.Adapter {


    private final Context context;
    private final List<HomeBean.DataBean.ItemsBean.ListBeanX> datas;

    /**
     * HomeType==1
     */
    private static final int TYPE_ONE = 0;

    /**
     * HomeType==2
     */
    private static final int TYPE_TWO = 1;

    /**
     * HomeType==4
     */
    private static final int TYPE_FOR = 2;

    /**
     * HomeType==6
     */
    private static final int TYPE_SIX = 3;


    public HomeAdapter(Context context, List<HomeBean.DataBean.ItemsBean.ListBeanX> list) {
        this.context = context;
        this.datas = list;
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public int getItemViewType(int position) {
        int itemViewType = -1;
        //根据位置，从列表中得到一个数据对象
        int home_type = datas.get(position).getHome_type();
        Log.e("TAG", "type===" + type);
        if (home_type == 1) {
            itemViewType = TYPE_ONE;
        } else if (home_type == 2) {
            itemViewType = TYPE_TWO;
        } else if (home_type == 4) {
            itemViewType = TYPE_FOR;
        } else if (home_type == 6) {
            itemViewType = TYPE_SIX;
        }
        return itemViewType;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        Log.e("TAG", "viewType===" + viewType);

        switch (viewType) {
            case TYPE_ONE://1

                viewHolder = new OneHoder(View.inflate(context, R.layout.item_one, null));

                break;
            case TYPE_TWO://2

                viewHolder = new TwoHolder(View.inflate(context, R.layout.item_two, null));

                break;
            case TYPE_FOR://4

                viewHolder = new ForHolder(View.inflate(context, R.layout.item_for, null));

                break;
            case TYPE_SIX://6

                viewHolder = new SixHolder(View.inflate(context, R.layout.item_six, null));

                break;

        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == TYPE_ONE) {
            OneHoder oneHolder = (OneHoder) holder;
            oneHolder.setData(datas.get(position));

        } else if (getItemViewType(position) == TYPE_TWO) {
            TwoHolder twoHolder = (TwoHolder) holder;
            twoHolder.setData(datas.get(position));

        } else if (getItemViewType(position) == TYPE_FOR) {

            ForHolder forHolder = (ForHolder) holder;
            forHolder.setData(datas.get(position));


        } else if (getItemViewType(position) == TYPE_SIX) {

            SixHolder sixHolder = (SixHolder) holder;
            sixHolder.setData(datas.get(position));
        }
    }


    class OneHoder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_icon)
        ImageView ivIcon;

        public OneHoder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void setData(HomeBean.DataBean.ItemsBean.ListBeanX data) {
            Picasso.with(context)
                    .load(data.getOne().getPic_url())
                    .placeholder(R.drawable.atguigu_logo)
                    .error(R.drawable.atguigu_logo)
                    .into(ivIcon);
        }
    }

    class TwoHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_icon1)
        ImageView ivIcon1;
        @Bind(R.id.iv_icon2)
        ImageView ivIcon2;

        public TwoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(HomeBean.DataBean.ItemsBean.ListBeanX data) {
            Picasso.with(context)
                    .load(data.getOne().getPic_url())
                    .placeholder(R.drawable.atguigu_logo)
                    .error(R.drawable.atguigu_logo)
                    .into(ivIcon1);

            Picasso.with(context)
                    .load(data.getTwo().getPic_url())
                    .placeholder(R.drawable.atguigu_logo)
                    .error(R.drawable.atguigu_logo)
                    .into(ivIcon2);


        }
    }

    class ForHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_icon1)
        ImageView ivIcon1;
        @Bind(R.id.iv_icon2)
        ImageView ivIcon2;
        @Bind(R.id.iv_icon3)
        ImageView ivIcon3;
        @Bind(R.id.iv_icon4)
        ImageView ivIcon4;

        public ForHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(HomeBean.DataBean.ItemsBean.ListBeanX data) {

            Picasso.with(context)
                    .load(data.getOne().getPic_url())
                    .placeholder(R.drawable.atguigu_logo)
                    .error(R.drawable.atguigu_logo)
                    .into(ivIcon1);

            Picasso.with(context)
                    .load(data.getTwo().getPic_url())
                    .placeholder(R.drawable.atguigu_logo)
                    .error(R.drawable.atguigu_logo)
                    .into(ivIcon2);
            Picasso.with(context)
                    .load(data.getThree().getPic_url())
                    .placeholder(R.drawable.atguigu_logo)
                    .error(R.drawable.atguigu_logo)
                    .into(ivIcon3);

            Picasso.with(context)
                    .load(data.getFour().getPic_url())
                    .placeholder(R.drawable.atguigu_logo)
                    .error(R.drawable.atguigu_logo)
                    .into(ivIcon4);


        }
    }

    class SixHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.banner)
        Banner banner;

        public SixHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(HomeBean.DataBean.ItemsBean.ListBeanX data) {

            //设置Banner 数据
            List<String> images = new ArrayList<>();
            for (int i = 0; i < data.getList().size(); i++) {
                images.add(data.getList().get(i).getPic_url());
            }

            banner.setImages(images)
                    .setImageLoader(new GlideImageLoader())
                    .setOnBannerListener(new OnBannerListener() {
                        @Override
                        public void OnBannerClick(int position) {

                            UIUtils.showToast("position==" + position);

//                            if(position < banner_info.size()){
//                                String product_id = "";
//                                String name = "";
//                                String cover_price = "";
//                                if (position == 0) {
//                                    product_id = "627";
//                                    cover_price = "32.00";
//                                    name = "剑三T恤批发";
//                                } else if (position == 1) {
//                                    product_id = "21";
//                                    cover_price = "8.00";
//                                    name = "同人原创】剑网3 剑侠情缘叁 Q版成男 口袋胸针";
//                                } else {
//                                    product_id = "1341";
//                                    cover_price = "50.00";
//                                    name = "【蓝诺】《天下吾双》 剑网3同人本";
//                                }
//                                String image = banner_info.get(position).getImage();
//                                GoodsBean goodsBean = new GoodsBean();
//                                goodsBean.setName(name);
//                                goodsBean.setCover_price(cover_price);
//                                goodsBean.setFigure(image);
//                                goodsBean.setProduct_id(product_id);
//
//                                Intent intent = new Intent(mContext, GoodsInfoActivity.class);
//                                intent.putExtra(GOODS_BEAN, goodsBean);
//                                mContext.startActivity(intent);
//                            }
                        }
                    })
                    .start();

        }
    }

}