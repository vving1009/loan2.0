package com.jiaye.cashloan.view.data.my.credit.bank.source;

import com.jiaye.cashloan.http.data.my.CreditUnBindBank;
import com.jiaye.cashloan.http.data.my.CreditUnBindBankRequest;
import com.jiaye.cashloan.http.utils.ResponseTransformer;

import io.reactivex.Flowable;

/**
 * CreditBankRepository
 *
 * @author 贾博瑄
 */

public class CreditBankRepository implements CreditBankDataSource {

    @Override
    public Flowable<CreditUnBindBank> unBind() {
        return Flowable.just(new CreditUnBindBankRequest())
                .compose(new ResponseTransformer<CreditUnBindBankRequest, CreditUnBindBank>("creditBankUnBind"));
    }
}
