package com.luomor.yiaroundad.network.api;

import com.luomor.yiaroundad.entity.discover.HotSearchTag;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Peter on 2018/06/3 11:53
 * 1097692918@qq.com
 * <p>
 * 搜索相关api
 */

public interface SearchService {

    /**
     * 首页发现热搜词
     */
    @GET("main/hotword?access_key=ec0f54fc369d8c104ee1068672975d6a&actionKey=appkey&appkey=27eb53fc9058f8c3")
    Observable<HotSearchTag> getHotSearchTags();
}
