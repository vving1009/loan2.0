package com.jiaye.cashloan.view.info2.source;

import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.saveauth.SaveAuthRequest;

import io.reactivex.Flowable;

/**
 * Info2DataSource
 *
 * @author 贾博瑄
 */

public interface Info2DataSource {

    Flowable<EmptyResponse> submit(SaveAuthRequest request);

    Flowable<EmptyResponse> requestUpdateStep();
}
