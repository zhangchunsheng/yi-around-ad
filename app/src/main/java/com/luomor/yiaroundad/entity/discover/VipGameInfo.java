package com.luomor.yiaroundad.entity.discover;

/**
 * Created by Peter on 2018/06/31 23:26
 * 1097692918@qq.com
 * <p>
 * 易会员游戏礼包模型类
 */

public class VipGameInfo {
    private int code;
    private DataBean result;
    private String msg;

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

    public DataBean getResult() {
        return result;
    }

    public void setResult(DataBean result) {
        this.result = result;
    }

    public static class DataBean {
        private String link;
        private String imgPath;

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getImgPath() {
            return imgPath;
        }

        public void setImgPath(String imgPath) {
            this.imgPath = imgPath;
        }
    }
}
