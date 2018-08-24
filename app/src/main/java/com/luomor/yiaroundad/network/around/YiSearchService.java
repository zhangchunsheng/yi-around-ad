package com.luomor.yiaroundad.network.around;

import com.luomor.yiaroundad.entity.discover.HotSearchTag;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by peterzhang on 24/08/2018.
 */

public interface YiSearchService {
    /**
     * 首页发现热搜词
     */
    @GET("hotword/getHotWordList?version=1")
    Observable<HotSearchTag> getHotSearchTags();
}
