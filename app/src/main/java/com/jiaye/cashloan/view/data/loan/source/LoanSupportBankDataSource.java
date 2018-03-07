package com.jiaye.cashloan.view.data.loan.source;

import com.jiaye.cashloan.http.data.loan.SupportBankList;

import java.util.List;

import io.reactivex.Flowable;

/**
 * LoanSupportBankDataSource
 *
 * @author 贾博瑄
 */

public interface LoanSupportBankDataSource {

    Flowable<List<SupportBankList.Data>> supportBankList();
}
