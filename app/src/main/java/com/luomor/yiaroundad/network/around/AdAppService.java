package com.luomor.yiaroundad.network.around;

import com.luomor.yiaroundad.entity.recommend.RecommendBannerInfo;
import com.luomor.yiaroundad.entity.recommend.RecommendInfo;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by peterzhang on 22/08/2018.
 */

public interface AdAppService {
    /**
     * 首页推荐数据
     */
    @GET("recommend/getList?version=1")
    Observable<RecommendInfo> getRecommendedInfo();

    /**
     * 首页推荐banner
     */
    @GET("recommend/getBanners?version=1")
    Observable<RecommendBannerInfo> getRecommendedBannerInfo();
}
