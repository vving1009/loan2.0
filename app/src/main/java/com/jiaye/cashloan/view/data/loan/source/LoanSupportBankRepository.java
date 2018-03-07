package com.jiaye.cashloan.view.data.loan.source;

import com.jiaye.cashloan.http.data.loan.SupportBankList;
import com.jiaye.cashloan.http.data.loan.SupportBankListRequest;
import com.jiaye.cashloan.http.utils.ResponseTransformer;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * LoanSupportBankRepository
 *
 * @author 贾博瑄
 */

public class LoanSupportBankRepository implements LoanSupportBankDataSource {

    @Override
    public Flowable<List<SupportBankList.Data>> supportBankList() {
        return Flowable.just(new SupportBankListRequest())
                .compose(new ResponseTransformer<SupportBankListRequest, SupportBankList>("supportBankList"))
                .map(new Function<SupportBankList, List<SupportBankList.Data>>() {
                    @Override
                    public List<SupportBankList.Data> apply(SupportBankList supportBankList) throws Exception {
                        return supportBankList.getList();
                    }
                });
    }
}
