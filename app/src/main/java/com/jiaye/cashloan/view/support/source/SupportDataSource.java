package com.jiaye.cashloan.view.support.source;

import com.jiaye.cashloan.http.data.loan.SupportBankList;

import java.util.List;

import io.reactivex.Flowable;

/**
 * SupportDataSource
 *
 * @author 贾博瑄
 */

public interface SupportDataSource {

    Flowable<List<SupportBankList.Data>> supportBankList();
}
