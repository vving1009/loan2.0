package com.jiaye.cashloan.view.bankcard.source;

import com.jiaye.cashloan.http.data.my.CreditUnBindBank;
import com.jiaye.cashloan.http.data.my.CreditUnBindBankRequest;
import com.jiaye.cashloan.http.utils.ResponseTransformer;

import io.reactivex.Flowable;

/**
 * BankCardRepository
 *
 * @author 贾博瑄
 */

public class BankCardRepository implements BankCardDataSource {

    @Override
    public Flowable<CreditUnBindBank> unBind() {
        return Flowable.just(new CreditUnBindBankRequest())
                .compose(new ResponseTransformer<CreditUnBindBankRequest, CreditUnBindBank>("creditBankUnBind"));
    }
}
