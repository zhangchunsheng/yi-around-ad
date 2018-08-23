package com.luomor.yiaroundad.network.around;

import com.luomor.yiaroundad.entity.live.LiveAppIndexInfo;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by peterzhang on 22/08/2018.
 */

public interface YiLiveService {
    /**
     * 首页直播
     */
    @GET("live/getRecommendList")
    Observable<LiveAppIndexInfo> getLiveAppIndex();

    /**
     * 直播url
     */
    @GET("live/getPlayerUrl")
    Observable<ResponseBody> getLiveUrl(@Query("cid") int cid);
}
