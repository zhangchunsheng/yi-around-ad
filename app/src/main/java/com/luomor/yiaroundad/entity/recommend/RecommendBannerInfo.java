package com.luomor.yiaroundad.entity.recommend;

import java.util.List;

/**
 * Created by Peter on 2018/06/24 19:25
 * 1097692918@qq.com
 * <p>
 * 首页Banner推荐
 */

public class RecommendBannerInfo {
    private int code;
    private String msg;

    private List<DataBean> result;

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

    public List<DataBean> getResult() {
        return result;
    }

    public void setResult(List<DataBean> result) {
        this.result = result;
    }


    public static class DataBean {
        private String title;
        private String value;
        private String image;
        private int type;
        private int weight;
        private String remark;
        private String hash;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }
    }
}
