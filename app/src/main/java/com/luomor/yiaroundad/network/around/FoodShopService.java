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
    @GET("shop/getChineseFood")
    Observable<FoodScheduleInfo> getFoodSchedules();

    /**
     * 美食索引
     */
    @GET("shop/getShopType")
    Observable<FoodIndexInfo> getFoodIndex();
}
