package com.jiaye.cashloan.view.data.my.certificate.operator.source;

import com.jiaye.cashloan.http.data.my.Phone;

import io.reactivex.Flowable;

/**
 * OperatorDataSource
 *
 * @author 贾博瑄
 */

public interface OperatorDataSource {

    Flowable<Phone> phone();
}
