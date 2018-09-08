package com.luomor.yiaroundad.network.around;

import com.luomor.yiaroundad.entity.food.FoodDetailsInfo;
import com.luomor.yiaroundad.entity.food.FoodDetailsRecommendInfo;
import com.luomor.yiaroundad.entity.food.FoodIndexInfo;
import com.luomor.yiaroundad.entity.food.FoodScheduleInfo;
import com.luomor.yiaroundad.entity.shop.FoodShopRecommendInfo;
import com.luomor.yiaroundad.entity.shop.ShopListInfo;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by peterzhang on 20/08/2018.
 */

public interface FoodShopService {
    /**
     * 首页美食
     */
    @GET("shop/getShopList")
    Observable<ShopListInfo> getShopList(@Query("shop_type") String shopType, @Query("latitude") double latitude, @Query("longitude") double longitude);

    /**
     * 首页美食推荐
     */
    @GET("shop/getRecommendShopList")
    Observable<FoodShopRecommendInfo> getFoodRecommended(@Query("shop_type") String shopType, @Query("latitude") double latitude, @Query("longitude") double longitude);

    /**
     * 美食详情
     */
    @GET("shop/getShopDetail?shop_id=1")
    Observable<FoodDetailsInfo> getShopDetails();

    /**
     * 美食详情美食推荐
     */
    @GET("shop/getRecommendByShop?shop_id=1")
    Observable<FoodDetailsRecommendInfo> getFoodDetailsRecommend();

    /**
     * 美食时间表
     */
    @GET("api/timeline_v4?access_key=19946e1ef3b5cad1a756c475a67185bb&actionKey=appkey&appkey=27eb53fc9058f8c3&area_id=2&build=3940&device=phone&mobi_app=iphone&platform=ios&see_mine=0&sign=d8cbbacab2e5fd0196b4883201e2103e&ts=1477981526")
    Observable<FoodScheduleInfo> getFoodSchedules();

    /**
     * 美食索引
     */
    @GET("api/bangumi_index_cond?access_key=19946e1ef3b5cad1a756c475a67185bb&actionKey=appkey&appkey=27eb53fc9058f8c3&build=3940&device=phone&mobi_app=iphone&platform=ios&sign=cfc6903a13ba89e81c904b4c589e5369&ts=1477974966&type=0")
    Observable<FoodIndexInfo> getFoodIndex();
}
