package com.luomor.yiaroundad.network.around;

import com.luomor.yiaroundad.entity.shop.FoodShopRecommendInfo;
import com.luomor.yiaroundad.entity.shop.ShopListInfo;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by peterzhang on 20/08/2018.
 */

public interface FoodShopService {
    /**
     * 首页美食
     */
    @GET("shop/getShopList?shop_type=001")
    Observable<ShopListInfo> getShopList();

    @GET("shop/getRecommendShopList?shop_type=001")
    Observable<FoodShopRecommendInfo> getFoodRecommended();
}
