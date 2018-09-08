package com.luomor.yiaroundad.network.around;

import com.luomor.yiaroundad.entity.recommend.RecommendBannerInfo;
import com.luomor.yiaroundad.entity.recommend.RecommendInfo;
import com.luomor.yiaroundad.entity.search.SearchArchiveInfo;
import com.luomor.yiaroundad.entity.search.SearchFoodInfo;
import com.luomor.yiaroundad.entity.search.SearchMovieInfo;
import com.luomor.yiaroundad.entity.search.SearchSpInfo;
import com.luomor.yiaroundad.entity.search.SearchUpperInfo;
import com.luomor.yiaroundad.entity.video.VideoDetailsInfo;

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
    @GET("search/getAll")
    Observable<SearchArchiveInfo> searchArchive(
            @Query("keyword") String content, @Query("pn") int page, @Query("ps") int pagesize);

    /**
     * 美食搜索
     */
    @GET("search/getShops")
    Observable<SearchFoodInfo> searchFood(
            @Query("keyword") String content, @Query("pn") int page, @Query("ps") int pagesize);

    /**
     * 商品搜索
     */
    @GET("search/getItems")
    Observable<SearchUpperInfo> searchUpper(
            @Query("keyword") String content, @Query("pn") int page, @Query("ps") int pagesize);

    /**
     * 影视搜索
     */
    @GET("search/getLives")
    Observable<SearchMovieInfo> searchMovie(
            @Query("keyword") String content, @Query("pn") int page, @Query("ps") int pagesize);

    /**
     * 专题搜索
     */
    @GET("search/getSubjects")
    Observable<SearchSpInfo> searchSp(
            @Query("keyword") String content, @Query("pn") int page, @Query("ps") int pagesize);

    /**
     * 视频详情数据
     */
    @GET("video/getVideoDetail")
    Observable<VideoDetailsInfo> getShopDetails(@Query("shop_id") int shopId);
}
