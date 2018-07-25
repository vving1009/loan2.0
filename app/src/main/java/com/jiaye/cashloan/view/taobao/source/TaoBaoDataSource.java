package com.jiaye.cashloan.view.taobao.source;

import com.jiaye.cashloan.http.base.EmptyResponse;

import io.reactivex.Flowable;

/**
 * TaoBaoDataSource
 *
 * @author 贾博瑄
 */

public interface TaoBaoDataSource {

    Flowable<EmptyResponse> requestTaoBao();
}
