package com.luomor.yiaroundad.network.around;

import com.luomor.yiaroundad.entity.discover.AllareasRankInfo;
import com.luomor.yiaroundad.entity.discover.OriginalRankInfo;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by peterzhang on 26/08/2018.
 */

public interface YiRankService {
    /**
     * 原创排行榜请求
     */
    @GET("rank/getShopRank")
    Observable<OriginalRankInfo> getOriginalRanks(@Query("shop_type") String shopType);

    /**
     * 全区排行榜数据请求
     */
    @GET("index/rank/{type}")
    Observable<AllareasRankInfo> getAllareasRanks(@Path("type") String type);
}
