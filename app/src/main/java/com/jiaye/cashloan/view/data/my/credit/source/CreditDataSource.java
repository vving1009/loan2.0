package com.jiaye.cashloan.view.data.my.credit.source;

import com.jiaye.cashloan.view.data.my.credit.CreditBalanceRequest;
import com.jiaye.cashloan.view.data.my.credit.CreditCashRequest;
import com.jiaye.cashloan.view.data.my.credit.CreditPasswordRequest;

import io.reactivex.Flowable;

/**
 * CreditDataSource
 *
 * @author 贾博瑄
 */

public interface CreditDataSource {

    Flowable<CreditPasswordRequest> password();

    Flowable<CreditCashRequest> cash();

    Flowable<CreditBalanceRequest> balance();
}
