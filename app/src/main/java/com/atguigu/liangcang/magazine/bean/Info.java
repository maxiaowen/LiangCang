package com.atguigu.liangcang.magazine.bean;

/**
 * Created by Administrator on 2017/7/13.
 */

public class Info {

//            "taid":"2062",
//            "topic_name":"他用一张196㎡的纸还原出世界上最后一头白犀牛",
//            "cat_id":"23",
//            "author_id":"1",
//            "topic_url":"http://www.iliangcang.com/i/topicapp/201707100852",
//            "access_url":"http://www.iliangcang.com/i/topicapp/201707100852",
//            "cover_img":"http://imgs-qn.iliangcang.com/ware/appimg/topic/cover/2062_.jpg?_t=1499788876",
//            "cover_img_new":"http://imgs-qn.iliangcang.com/ware/appimg/topic/cover/2062_.jpg?_t=1499788876",
//            "hit_number":452,
//            "addtime":"2017-07-12 18:09:31",
//            "content":"",
//            "nav_title":"良仓杂志",
//            "author_name":"良仓",
//            "cat_name":"视频"


    private String taid;
    private String topic_name;
    private String cat_id;
    private String author_id;
    private String topic_url;
    private String access_url;
    private String cover_img;
    private String cover_img_new;
    private int hit_number;
    private String addtime;
    private String content;
    private String nav_title;
    private String author_name;
    private String cat_name;

    public String getTaid() {
        return taid;
    }

    public void setTaid(String taid) {
        this.taid = taid;
    }

    public String getTopic_name() {
        return topic_name;
    }

    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public String getTopic_url() {
        return topic_url;
    }

    public void setTopic_url(String topic_url) {
        this.topic_url = topic_url;
    }

    public String getAccess_url() {
        return access_url;
    }

    public void setAccess_url(String access_url) {
        this.access_url = access_url;
    }

    public String getCover_img() {
        return cover_img;
    }

    public void setCover_img(String cover_img) {
        this.cover_img = cover_img;
    }

    public String getCover_img_new() {
        return cover_img_new;
    }

    public void setCover_img_new(String cover_img_new) {
        this.cover_img_new = cover_img_new;
    }

    public int getHit_number() {
        return hit_number;
    }

    public void setHit_number(int hit_number) {
        this.hit_number = hit_number;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNav_title() {
        return nav_title;
    }

    public void setNav_title(String nav_title) {
        this.nav_title = nav_title;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }


    @Override
    public String toString() {
        return "Info{" +
                "taid='" + taid + '\'' +
                ", topic_name='" + topic_name + '\'' +
                ", cat_id='" + cat_id + '\'' +
                ", author_id='" + author_id + '\'' +
                ", topic_url='" + topic_url + '\'' +
                ", access_url='" + access_url + '\'' +
                ", cover_img='" + cover_img + '\'' +
                ", cover_img_new='" + cover_img_new + '\'' +
                ", hit_number=" + hit_number +
                ", addtime='" + addtime + '\'' +
                ", content='" + content + '\'' +
                ", nav_title='" + nav_title + '\'' +
                ", author_name='" + author_name + '\'' +
                ", cat_name='" + cat_name + '\'' +
                '}';
    }
}
