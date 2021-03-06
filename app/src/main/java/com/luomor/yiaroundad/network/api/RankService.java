package com.luomor.yiaroundad.network.api;

import com.luomor.yiaroundad.entity.discover.AllareasRankInfo;
import com.luomor.yiaroundad.entity.discover.OriginalRankInfo;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Peter on 2018/06/22 18:40
 * 1097692918@qq.com
 * <p>
 * 排行榜相关api
 */

public interface RankService {

    /**
     * 原创排行榜请求
     */
    @GET("index/rank/{type}")
    Observable<OriginalRankInfo> getOriginalRanks(@Path("type") String type);

    /**
     * 全区排行榜数据请求
     */
    @GET("index/rank/{type}")
    Observable<AllareasRankInfo> getAllareasRanks(@Path("type") String type);
}
