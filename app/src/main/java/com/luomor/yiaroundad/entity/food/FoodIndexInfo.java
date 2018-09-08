package com.luomor.yiaroundad.entity.food;

import java.util.List;

/**
 * Created by Peter on 18/6/6 11:08
 * 1097692918@qq.com
 * <p/>
 * 美食索引模型类
 */
public class FoodIndexInfo {
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
        private List<CategoryBean> category;
        private List<String> years;

        public List<CategoryBean> getCategory() {
            return category;
        }

        public void setCategory(List<CategoryBean> category) {
            this.category = category;
        }

        public List<String> getYears() {
            return years;
        }

        public void setYears(List<String> years) {
            this.years = years;
        }

        public static class CategoryBean {
            private String cover;
            private String tag_id;
            private String tag_name;

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getTag_id() {
                return tag_id;
            }

            public void setTag_id(String tag_id) {
                this.tag_id = tag_id;
            }

            public String getTag_name() {
                return tag_name;
            }

            public void setTag_name(String tag_name) {
                this.tag_name = tag_name;
            }
        }
    }
}
