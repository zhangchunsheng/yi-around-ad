package com.luomor.yiaroundad.network.around;

import com.luomor.yiaroundad.entity.recommend.RecommendBannerInfo;
import com.luomor.yiaroundad.entity.recommend.RecommendInfo;
import com.luomor.yiaroundad.entity.search.SearchArchiveInfo;

import retrofit2.http.GET;
import retrofit2.http.Query;
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

    /**
     * 综合搜索
     */
    @GET("x/v2/search?actionKey=appkey&appkey=27eb53fc9058f8c3&build=3710&device=phone&duration=0&mobi_app=iphone&order=default&platform=ios&rid=0")
    Observable<SearchArchiveInfo> searchArchive(
            @Query("keyword") String content, @Query("pn") int page, @Query("ps") int pagesize);
}
