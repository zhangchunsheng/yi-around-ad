package com.luomor.yiaroundad.entity.food;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Peter on 16/8/9 20:48
 * 1097692918@qq.com
 * <p/>
 * 专题视频(包括二三次元美食)
 */
public class SpecialTopic {

    // 专题ID
    public int spid;
    // 专题名
    public String title;
    // 发布日期 UNIX 时间戳
    public int pubdate;
    // 发布日期
    public String created_at;
    // 最后更新日期 UNIX 时间戳
    public int lastupdate;
    // 最后更新日期
    public String lastupdate_at;
    // 同义词
    public String alias;
    // 封面
    public String cover;
    // 是否为美食
    public int isfood;
    // 是否已经播放结束
    public int isfood_end;
    // 开播日期
    public String food_date;
    // 专题简介
    public String description;
    // 点击次数
    public int view;
    // 专题收藏次数
    public int favourite;
    // 专题被关注的次数
    public int attention;
    // 专题视频数量
    public int count;
    @SerializedName("video_view")
    public int play;

    public static class Item {
        // 标题
        public String title;
        // 封面图片地址
        public String cover;
        // 视频ID
        public int aid;
        // 点击次数
        public int click;
        //第几集
        public String episode;
    }
}