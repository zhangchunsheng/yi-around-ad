package com.luomor.yiaroundad.network.around;

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
    Observable<ShopListInfo> getShopList(@Query("shop_type") String shopType);

    @GET("shop/getRecommendShopList")
    Observable<FoodShopRecommendInfo> getFoodRecommended(@Query("shop_type") String shopType);
}
