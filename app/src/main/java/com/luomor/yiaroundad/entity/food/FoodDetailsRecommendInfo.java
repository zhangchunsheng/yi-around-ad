package com.luomor.yiaroundad.entity.food;

import java.util.List;

/**
 * Created by Peter on 2016/10/31 22:27
 * 1097692918@qq.com
 * <p>
 * 美食详情美食推荐模型类
 */

public class FoodDetailsRecommendInfo {
    private int code;
    private String message;
    private ResultBean result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private String season_id;
        private String title;
        private List<ListBean> list;

        public String getSeason_id() {
            return season_id;
        }

        public void setSeason_id(String season_id) {
            this.season_id = season_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private String food_id;
            private String cover;
            private String follow;
            private String isfinish;
            private String season_id;
            private String title;
            private String total_count;

            public String getFood_id() {
                return food_id;
            }

            public void setFood_id(String food_id) {
                this.food_id = food_id;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getFollow() {
                return follow;
            }

            public void setFollow(String follow) {
                this.follow = follow;
            }

            public String getIsfinish() {
                return isfinish;
            }

            public void setIsfinish(String isfinish) {
                this.isfinish = isfinish;
            }

            public String getSeason_id() {
                return season_id;
            }

            public void setSeason_id(String season_id) {
                this.season_id = season_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTotal_count() {
                return total_count;
            }

            public void setTotal_count(String total_count) {
                this.total_count = total_count;
            }
        }
    }
}
