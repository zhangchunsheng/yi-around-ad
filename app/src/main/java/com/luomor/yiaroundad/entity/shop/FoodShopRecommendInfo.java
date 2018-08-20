package com.luomor.yiaroundad.entity.shop;

import java.util.List;

/**
 * Created by peterzhang on 20/08/2018.
 */

public class FoodShopRecommendInfo {
    private int code;
    private String msg;
    private List<ResultBean> result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        private String cover;
        private long cursor;
        private String desc;
        private int id;
        private int is_new;
        private String link;
        private String title;

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public long getCursor() {
            return cursor;
        }

        public void setCursor(long cursor) {
            this.cursor = cursor;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIs_new() {
            return is_new;
        }

        public void setIs_new(int is_new) {
            this.is_new = is_new;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
