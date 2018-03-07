package com.jiaye.cashloan.view.data.my.credit.source;

import com.jiaye.cashloan.http.data.auth.Auth;
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

    Flowable<Auth> requestAuth();

    Flowable<CreditPasswordRequest> password();

    Flowable<CreditPasswordResetRequest> passwordReset();

    Flowable<CreditBalance> balance();

    Flowable<CreditInfo> creditInfo();
}
