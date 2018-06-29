package com.luomor.yiaroundad.network.api;

import com.luomor.yiaroundad.entity.user.UserDetailsInfo;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Peter on 16/8/8 20:48
 * 1097692918@qq.com
 * <p>
 * 用户个人账号相关api
 */
public interface AccountService {

    /**
     * 用户详情数据
     */
    @GET("api/member/getCardByMid")
    Observable<UserDetailsInfo> getUserInfoById(@Query("mid") int mid);
}
