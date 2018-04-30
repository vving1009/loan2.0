package com.jiaye.cashloan.view.data.my.certificate.idcard.source;

import com.jiaye.cashloan.http.data.my.IDCardAuth;
import com.jiaye.cashloan.http.data.my.IDCardAuthRequest;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;

import io.reactivex.Flowable;

/**
 * IdCardRepository
 *
 * @author 贾博瑄
 */

public class IdCardRepository implements IdCardDataSource {

    @Override
    public Flowable<IDCardAuth> idCardAuth() {
        return Flowable.just(new IDCardAuthRequest())
                .compose(new SatcatcheResponseTransformer<IDCardAuthRequest, IDCardAuth>
                        ("idCardAuth"));
    }
}
