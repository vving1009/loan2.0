package com.jiaye.cashloan.view.data.my.certificate.operator.source;

import com.jiaye.cashloan.http.data.my.Phone;
import com.jiaye.cashloan.http.data.my.PhoneRequest;
import com.jiaye.cashloan.http.utils.ResponseTransformer;

import io.reactivex.Flowable;

/**
 * OperatorRepository
 *
 * @author 贾博瑄
 */

public class OperatorRepository implements OperatorDataSource {

    @Override
    public Flowable<Phone> phone() {
        return Flowable.just(new PhoneRequest())
                .compose(new ResponseTransformer<PhoneRequest, Phone>("phone"));
    }
}
