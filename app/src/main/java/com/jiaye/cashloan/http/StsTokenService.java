package com.jiaye.cashloan.http;

import com.jiaye.cashloan.http.data.ststoken.StsTokenRequest;
import com.jiaye.cashloan.http.data.ststoken.StsTokenResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface StsTokenService {
    /**
     * 是否可以借款
     */
    @POST("sts")
    Call<StsTokenResponse> getToken(@Body StsTokenRequest request);
}
