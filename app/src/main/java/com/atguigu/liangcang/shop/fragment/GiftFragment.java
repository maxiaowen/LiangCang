package com.atguigu.liangcang.shop.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.atguigu.liangcang.R;
import com.atguigu.liangcang.base.BaseFragment;
import com.atguigu.liangcang.shop.activity.DetailsActivity;
import com.atguigu.liangcang.utils.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/6.
 */

public class GiftFragment extends BaseFragment {


    @Bind(R.id.iv_gift)
    ImageView ivGift;
    @Bind(R.id.id_festival)
    ImageView idFestival;
    @Bind(R.id.id_love)
    ImageView idLove;
    @Bind(R.id.id_day)
    ImageView idDay;
    @Bind(R.id.id_friend)
    ImageView idFriend;
    @Bind(R.id.id_boy)
    ImageView idBoy;
    @Bind(R.id.id_parent)
    ImageView idParent;
    @Bind(R.id.iv_setting)
    ImageView ivSetting;

    private String[] urls = {
            //礼物
            "http://mobile.iliangcang.com/goods/goodsList?app_key=Android&count=10&list_id=7&page=1&sig=73760B2740FA36D5A273523FBC9295FE%7C285269230036268&v=1.0",
            //节日
            "http://mobile.iliangcang.com/goods/goodsList?app_key=Android&count=10&list_id=1&page=1&sig=DFD7151CC9D607E396FE108FE270FFF3%7C366534120395468&v=1.0",
            //爱情
            "http://mobile.iliangcang.com/goods/goodsList?app_key=Android&count=10&list_id=2&page=1&sig=73760B2740FA36D5A273523FBC9295FE%7C285269230036268&v=1.0",
            //生日
            "http://mobile.iliangcang.com/goods/goodsList?app_key=Android&count=10&list_id=3&page=1&sig=73760B2740FA36D5A273523FBC9295FE%7C285269230036268&v=1.0",
            //朋友
            "http://mobile.iliangcang.com/goods/goodsList?app_key=Android&count=10&list_id=4&page=1&sig=73760B2740FA36D5A273523FBC9295FE%7C285269230036268&v=1.0",
            //孩子
            "http://mobile.iliangcang.com/goods/goodsList?app_key=Android&count=10&list_id=5&page=1&sig=73760B2740FA36D5A273523FBC9295FE%7C285269230036268&v=1.0",
            //父母
            "http://mobile.iliangcang.com/goods/goodsList?app_key=Android&count=10&list_id=6&page=1&sig=73760B2740FA36D5A273523FBC9295FE%7C285269230036268&v=1.0",
    };

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_gift, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }



    @OnClick({R.id.iv_gift, R.id.id_festival, R.id.id_love, R.id.id_day, R.id.id_friend, R.id.id_boy, R.id.id_parent, R.id.iv_setting})
    public void onViewClicked(View view) {
        Intent intent = new Intent(context, DetailsActivity.class);
        switch (view.getId()) {
            case R.id.iv_gift:
                intent.putExtra("url", urls[0]);
                startActivity(intent);
                break;
            case R.id.id_festival:
                intent.putExtra("url", urls[1]);
                startActivity(intent);
                break;
            case R.id.id_love:
                intent.putExtra("url", urls[2]);
                startActivity(intent);
                break;
            case R.id.id_day:
                intent.putExtra("url", urls[3]);
                startActivity(intent);
                break;
            case R.id.id_friend:
                intent.putExtra("url", urls[4]);
                startActivity(intent);
                break;
            case R.id.id_boy:
                intent.putExtra("url", urls[5]);
                startActivity(intent);
                break;
            case R.id.id_parent:
                intent.putExtra("url", urls[6]);
                startActivity(intent);
                break;
            case R.id.iv_setting:
                UIUtils.showToast("还没有送出礼物哦！");
                break;
        }



    }
}
