package com.jiaye.cashloan.view.phone.source;

import com.jiaye.cashloan.http.base.EmptyResponse;

import io.reactivex.Flowable;

/**
 * PhoneDataSource
 *
 * @author 贾博瑄
 */

public interface PhoneDataSource {

    Flowable<EmptyResponse> requestUpdatePhone();
}
