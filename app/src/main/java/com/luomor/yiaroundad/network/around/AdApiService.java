package com.luomor.yiaroundad.network.around;

import com.luomor.yiaroundad.entity.attention.AttentionDynamicInfo;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by peterzhang on 24/08/2018.
 */

public interface AdApiService {
    /**
     * 首页关注
     */
    @GET("feed/getFeedList?user_id=1")
    Observable<AttentionDynamicInfo> getAttentionDynamic();
}
