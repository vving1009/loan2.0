package com.jiaye.cashloan.view.support.source;

import com.jiaye.cashloan.http.data.loan.SupportBankList;
import com.jiaye.cashloan.http.data.loan.SupportBankListRequest;
import com.jiaye.cashloan.http.utils.ResponseTransformer;

import java.util.List;

import io.reactivex.Flowable;

/**
 * SupportRepository
 *
 * @author 贾博瑄
 */

public class SupportRepository implements SupportDataSource {

    @Override
    public Flowable<List<SupportBankList.Data>> supportBankList() {
        return Flowable.just(new SupportBankListRequest())
                .compose(new ResponseTransformer<SupportBankListRequest, SupportBankList>
                        ("supportBankList"))
                .map(SupportBankList::getList);
    }
}
