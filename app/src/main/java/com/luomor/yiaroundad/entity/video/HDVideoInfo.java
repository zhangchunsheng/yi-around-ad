package com.luomor.yiaroundad.entity.video;

import java.util.List;

/**
 * Created by Peter on 18/6/30 19:50
 * 1097692918@qq.com
 * <p/>
 * 网站高清视频
 */
public class HDVideoInfo {

    private int code;
    private HDVideoBean result;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public HDVideoBean getResult() {
        return result;
    }

    public void setResult(HDVideoBean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class HDVideoBean {
        private String from;
        private String data;
        private String format;
        private int timelength;
        private String accept_format;
        private String seek_param;
        private String seek_type;
        private List<Integer> accept_quality;
        private List<DurlEntity> durl;

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public int getTimelength() {
            return timelength;
        }

        public void setTimelength(int timelength) {
            this.timelength = timelength;
        }

        public String getAccept_format() {
            return accept_format;
        }

        public void setAccept_format(String accept_format) {
            this.accept_format = accept_format;
        }

        public String getSeek_param() {
            return seek_param;
        }

        public void setSeek_param(String seek_param) {
            this.seek_param = seek_param;
        }

        public String getSeek_type() {
            return seek_type;
        }

        public void setSeek_type(String seek_type) {
            this.seek_type = seek_type;
        }

        public List<Integer> getAccept_quality() {
            return accept_quality;
        }

        public void setAccept_quality(List<Integer> accept_quality) {
            this.accept_quality = accept_quality;
        }

        public List<DurlEntity> getDurl() {
            return durl;
        }

        public void setDurl(List<DurlEntity> durl) {
            this.durl = durl;
        }
    }

    public static class DurlEntity {
        private int order;
        private int length;
        private int size;
        private String url;
        private List<String> backup_url;

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public List<String> getBackup_url() {
            return backup_url;
        }

        public void setBackup_url(List<String> backup_url) {
            this.backup_url = backup_url;
        }
    }
}
