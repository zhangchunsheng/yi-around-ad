package com.luomor.yiaroundad.widget.banner;

/**
 * Created by Peter on 16/8/24 21:37
 * 1097692918@qq.com
 * <p>
 * Banner模型类
 */
public class BannerEntity {
    public String title;
    public String img;
    public String link;

    public BannerEntity(String link, String title, String img) {
        this.link = link;
        this.title = title;
        this.img = img;
    }

}
