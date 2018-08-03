package com.jiaye.cashloan.view.account.source;

import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.my.CreditBalance;
import com.jiaye.cashloan.http.data.my.CreditInfo;
import com.jiaye.cashloan.http.data.my.CreditPasswordRequest;
import com.jiaye.cashloan.http.data.my.CreditPasswordResetRequest;
import com.jiaye.cashloan.http.data.my.CreditPasswordStatus;

import io.reactivex.Flowable;

/**
 * CreditDataSource
 *
 * @author 贾博瑄
 */

public interface CreditDataSource {

    Flowable<CreditPasswordStatus> passwordStatus();

    Flowable<CreditInfo> creditInfo();

    Flowable<EmptyResponse> saveBankCard(CreditInfo creditInfo);

    Flowable<CreditBalance> balance();

    Flowable<CreditPasswordRequest> password();

    Flowable<CreditPasswordResetRequest> passwordReset();
}
