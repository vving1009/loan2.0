package com.jiaye.cashloan.view.data.my.certificate.bank.source;

import com.jiaye.cashloan.http.data.my.Bank;
import com.jiaye.cashloan.http.data.my.BankRequest;
import com.jiaye.cashloan.http.utils.ResponseTransformer;

import io.reactivex.Flowable;

/**
 * BankRepository
 *
 * @author 贾博瑄
 */

public class BankRepository implements BankDataSource {

    @Override
    public Flowable<Bank> bank() {
        return Flowable.just(new BankRequest())
                .compose(new ResponseTransformer<BankRequest, Bank>("bank"));
    }
}
