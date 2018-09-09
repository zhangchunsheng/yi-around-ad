package com.luomor.yiaroundad.entity.config;

import com.luomor.yiaroundad.entity.shop.ShopListInfo;

/**
 * Created by peterzhang on 09/02/2018.
 */

public class ConfigBean {
    private int code;
    private String msg;
    private ResultBean result;

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

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * version: "1.0",
         * app_version: "1.0",
         * app_stable_version: "1.0",
         * upgrade_text: "",
         * event_id: 0,
         * server_time: 1518347677,
         * upgrade_url: ""
         */
        private String version;
        private String app_version;
        private String app_stable_version;
        private String upgrade_text;
        private int event_id;
        private int server_time;
        private String upgrade_url;

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getApp_version() {
            return app_version;
        }

        public void setApp_version(String app_version) {
            this.app_version = app_version;
        }

        public String getApp_stable_version() {
            return app_stable_version;
        }

        public void setApp_stable_version(String app_stable_version) {
            this.app_stable_version = app_stable_version;
        }

        public String getUpgrade_text() {
            return upgrade_text;
        }

        public void setUpgrade_text(String upgrade_text) {
            this.upgrade_text = upgrade_text;
        }

        public int getEvent_id() {
            return event_id;
        }

        public void setEvent_id(int event_id) {
            this.event_id = event_id;
        }

        public int getServer_time() {
            return server_time;
        }

        public void setServer_time(int server_time) {
            this.server_time = server_time;
        }

        public String getUpgrade_url() {
            return upgrade_url;
        }

        public void setUpgrade_url(String upgrade_url) {
            this.upgrade_url = upgrade_url;
        }
    }
}
