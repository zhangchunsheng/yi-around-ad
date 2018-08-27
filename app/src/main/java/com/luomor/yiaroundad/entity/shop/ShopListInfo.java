package com.luomor.yiaroundad.entity.shop;

import java.util.List;

/**
 * Created by peterzhang on 20/08/2018.
 */

public class ShopListInfo {
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

    public void setMsg(String message) {
        this.msg = message;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private List<ShopBean> shops;
        private LocationBean location;

        public List<ShopBean> getShops() {
            return shops;
        }

        public void setShops(List<ShopBean> shops) {
            this.shops = shops;
        }

        public LocationBean getLocation() {
            return location;
        }

        public void setLocation(LocationBean location) {
            this.location = location;
        }

        private AdBean ad;
        private PreviousBean previous;
        private List<SerializingBean> serializing;

        public AdBean getAd() {
            return ad;
        }

        public void setAd(AdBean ad) {
            this.ad = ad;
        }

        public PreviousBean getPrevious() {
            return previous;
        }


        public void setPrevious(PreviousBean previous) {
            this.previous = previous;
        }


        public List<SerializingBean> getSerializing() {
            return serializing;
        }


        public void setSerializing(List<SerializingBean> serializing) {
            this.serializing = serializing;
        }

        public static class LocationBean {
            private String formatted_address;

            public String getFormatted_address() {
                return formatted_address;
            }

            public void setFormatted_address(String formatted_address) {
                this.formatted_address = formatted_address;
            }
        }

        public static class AdBean {
            private List<AdBean.BodyBean> body;
            private List<AdBean.HeadBean> head;

            public List<AdBean.BodyBean> getBody() {
                return body;
            }


            public void setBody(List<AdBean.BodyBean> body) {
                this.body = body;
            }

            public List<AdBean.HeadBean> getHead() {
                return head;
            }

            public void setHead(List<AdBean.HeadBean> head) {
                this.head = head;
            }

            public static class BodyBean {
                private String img;
                private int index;
                private String link;
                private String title;

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public int getIndex() {
                    return index;
                }

                public void setIndex(int index) {
                    this.index = index;
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

            public static class HeadBean {
                private int id;
                private String img;
                private int is_ad;
                private String link;
                private String pub_time;
                private String title;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public int getIs_ad() {
                    return is_ad;
                }

                public void setIs_ad(int is_ad) {
                    this.is_ad = is_ad;
                }

                public String getLink() {
                    return link;
                }

                public void setLink(String link) {
                    this.link = link;
                }

                public String getPub_time() {
                    return pub_time;
                }

                public void setPub_time(String pub_time) {
                    this.pub_time = pub_time;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }
            }
        }

        public static class PreviousBean {
            private int season;
            private int year;
            private List<PreviousBean.ListBean> list;

            public int getSeason() {
                return season;
            }

            public void setSeason(int season) {
                this.season = season;
            }

            public int getYear() {
                return year;
            }

            public void setYear(int year) {
                this.year = year;
            }

            public List<PreviousBean.ListBean> getList() {
                return list;
            }

            public void setList(List<PreviousBean.ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                private String cover;
                private String favourites;
                private int is_finish;
                private int last_time;
                private String newest_ep_index;
                private int pub_time;
                private int season_id;
                private int season_status;
                private String title;
                private int watching_count;
                private int recommend_num;
                private int distance;

                public int getRecommend_num() {
                    return recommend_num;
                }

                public void setRecommend_num(int recommend_num) {
                    this.recommend_num = recommend_num;
                }

                public int getDistance() {
                    return distance;
                }

                public void setDistance(int distance) {
                    this.distance = distance;
                }

                public String getCover() {
                    return cover;
                }

                public void setCover(String cover) {
                    this.cover = cover;
                }

                public String getFavourites() {
                    return favourites;
                }

                public void setFavourites(String favourites) {
                    this.favourites = favourites;
                }

                public int getIs_finish() {
                    return is_finish;
                }

                public void setIs_finish(int is_finish) {
                    this.is_finish = is_finish;
                }

                public int getLast_time() {
                    return last_time;
                }

                public void setLast_time(int last_time) {
                    this.last_time = last_time;
                }

                public String getNewest_ep_index() {
                    return newest_ep_index;
                }

                public void setNewest_ep_index(String newest_ep_index) {
                    this.newest_ep_index = newest_ep_index;
                }

                public int getPub_time() {
                    return pub_time;
                }

                public void setPub_time(int pub_time) {
                    this.pub_time = pub_time;
                }

                public int getSeason_id() {
                    return season_id;
                }

                public void setSeason_id(int season_id) {
                    this.season_id = season_id;
                }

                public int getSeason_status() {
                    return season_status;
                }

                public void setSeason_status(int season_status) {
                    this.season_status = season_status;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public int getWatching_count() {
                    return watching_count;
                }

                public void setWatching_count(int watching_count) {
                    this.watching_count = watching_count;
                }
            }
        }

        public static class SerializingBean {
            private String cover;
            private String favourites;
            private int is_finish;
            private int is_started;
            private int last_time;
            private String newest_ep_index;
            private int pub_time;
            private int season_id;
            private int season_status;
            private String title;
            private int watching_count;

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getFavourites() {
                return favourites;
            }

            public void setFavourites(String favourites) {
                this.favourites = favourites;
            }

            public int getIs_finish() {
                return is_finish;
            }

            public void setIs_finish(int is_finish) {
                this.is_finish = is_finish;
            }

            public int getIs_started() {
                return is_started;
            }

            public void setIs_started(int is_started) {
                this.is_started = is_started;
            }

            public int getLast_time() {
                return last_time;
            }

            public void setLast_time(int last_time) {
                this.last_time = last_time;
            }

            public String getNewest_ep_index() {
                return newest_ep_index;
            }

            public void setNewest_ep_index(String newest_ep_index) {
                this.newest_ep_index = newest_ep_index;
            }

            public int getPub_time() {
                return pub_time;
            }

            public void setPub_time(int pub_time) {
                this.pub_time = pub_time;
            }

            public int getSeason_id() {
                return season_id;
            }

            public void setSeason_id(int season_id) {
                this.season_id = season_id;
            }

            public int getSeason_status() {
                return season_status;
            }

            public void setSeason_status(int season_status) {
                this.season_status = season_status;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getWatching_count() {
                return watching_count;
            }

            public void setWatching_count(int watching_count) {
                this.watching_count = watching_count;
            }
        }

        public static class ShopBean {
            private long shop_id;
            private String shop_name;
            private String shop_sname;
            private String shop_type;
            private String shop_link;
            private String shop_desc;
            private String shop_image;
            private String remark;
            private int star_level;
            private int comment_num;
            private int avg_price;
            private int comment_score;
            private int taste_score;
            private int environment_score;
            private int service_score;
            private String phone_num;
            private long flag;
            private String dianping_link;
            private String dianping_id;
            private int dianping_page_num;
            private String dianping_image;
            private int groupon_num;
            private String country;
            private String city;
            private String province_code;
            private String city_code;
            private String district_code;
            private String business_district;
            private String address;
            private double latitude;
            private double longitude;
            private int item_num;
            private int founding_time;
            private String location;
            private String business_hours;
            private int founding_year;

            public long getShop_id() {
                return shop_id;
            }

            public String getShop_name() {
                return shop_name;
            }

            public String getShop_sname() {
                return shop_sname;
            }

            public String getShop_type() {
                return shop_type;
            }

            public String getShop_link() {
                return shop_link;
            }

            public String getShop_desc() {
                return shop_desc;
            }

            public String getShop_image() {
                return shop_image;
            }

            public String getRemark() {
                return remark;
            }

            public int getStar_level() {
                return star_level;
            }

            public int getComment_num() {
                return comment_num;
            }

            public int getAvg_price() {
                return avg_price;
            }

            public int getComment_score() {
                return comment_score;
            }

            public int getTaste_score() {
                return taste_score;
            }

            public int getEnvironment_score() {
                return environment_score;
            }

            public int getService_score() {
                return service_score;
            }

            public String getPhone_num() {
                return phone_num;
            }

            public long getFlag() {
                return flag;
            }

            public String getDianping_link() {
                return dianping_link;
            }

            public String getDianping_id() {
                return dianping_id;
            }

            public int getDianping_page_num() {
                return dianping_page_num;
            }

            public String getDianping_image() {
                return dianping_image;
            }

            public int getGroupon_num() {
                return groupon_num;
            }

            public String getCountry() {
                return country;
            }

            public String getCity() {
                return city;
            }

            public String getProvince_code() {
                return province_code;
            }

            public String getCity_code() {
                return city_code;
            }

            public String getDistrict_code() {
                return district_code;
            }

            public String getBusiness_district() {
                return business_district;
            }

            public String getAddress() {
                return address;
            }

            public double getLatitude() {
                return latitude;
            }

            public double getLongitude() {
                return longitude;
            }

            public int getItem_num() {
                return item_num;
            }

            public int getFounding_time() {
                return founding_time;
            }

            public String getLocation() {
                return location;
            }

            public String getBusiness_hours() {
                return business_hours;
            }

            public int getFounding_year() {
                return founding_year;
            }

            public void setShop_id(long shop_id) {
                this.shop_id = shop_id;
            }

            public void setShop_name(String shop_name) {
                this.shop_name = shop_name;
            }

            public void setShop_sname(String shop_sname) {
                this.shop_sname = shop_sname;
            }

            public void setShop_type(String shop_type) {
                this.shop_type = shop_type;
            }

            public void setShop_link(String shop_link) {
                this.shop_link = shop_link;
            }

            public void setShop_desc(String shop_desc) {
                this.shop_desc = shop_desc;
            }

            public void setShop_image(String shop_image) {
                this.shop_image = shop_image;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public void setStar_level(int star_level) {
                this.star_level = star_level;
            }

            public void setComment_num(int comment_num) {
                this.comment_num = comment_num;
            }

            public void setAvg_price(int avg_price) {
                this.avg_price = avg_price;
            }

            public void setComment_score(int comment_score) {
                this.comment_score = comment_score;
            }

            public void setTaste_score(int taste_score) {
                this.taste_score = taste_score;
            }

            public void setEnvironment_score(int environment_score) {
                this.environment_score = environment_score;
            }

            public void setService_score(int service_score) {
                this.service_score = service_score;
            }

            public void setPhone_num(String phone_num) {
                this.phone_num = phone_num;
            }

            public void setFlag(long flag) {
                this.flag = flag;
            }

            public void setDianping_link(String dianping_link) {
                this.dianping_link = dianping_link;
            }

            public void setDianping_id(String dianping_id) {
                this.dianping_id = dianping_id;
            }

            public void setDianping_page_num(int dianping_page_num) {
                this.dianping_page_num = dianping_page_num;
            }

            public void setDianping_image(String dianping_image) {
                this.dianping_image = dianping_image;
            }

            public void setGroupon_num(int groupon_num) {
                this.groupon_num = groupon_num;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public void setProvince_code(String province_code) {
                this.province_code = province_code;
            }

            public void setCity_code(String city_code) {
                this.city_code = city_code;
            }

            public void setDistrict_code(String district_code) {
                this.district_code = district_code;
            }

            public void setBusiness_district(String business_district) {
                this.business_district = business_district;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public void setLatitude(double latitude) {
                this.latitude = latitude;
            }

            public void setLongitude(double longitude) {
                this.longitude = longitude;
            }

            public void setItem_num(int item_num) {
                this.item_num = item_num;
            }

            public void setFounding_time(int founding_time) {
                this.founding_time = founding_time;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public void setBusiness_hours(String business_hours) {
                this.business_hours = business_hours;
            }

            public void setFounding_year(int founding_year) {
                this.founding_year = founding_year;
            }
        }
    }
}
