package com.jiaye.cashloan.view.search.source;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.data.search.SaveSalesman;
import com.jiaye.cashloan.http.data.search.SaveSalesmanRequest;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;
import com.jiaye.cashloan.persistence.Salesman;

import java.util.List;

import io.reactivex.Flowable;

/**
 * SearchRepository
 *
 * @author 贾博瑄
 */

public class SearchRepository implements SearchDataSource {

    @Override
    public Flowable<List<Salesman>> queryPeople(String column, String value) {
        return Flowable.just(LoanApplication.getInstance().getDbHelper().querySales(column, value));
    }

    @Override
    public Flowable<SaveSalesman> saveSalesman(SaveSalesmanRequest request) {
        return Flowable.just(request)
                .doOnNext(request1 -> {
                    String loadId = LoanApplication.getInstance().getDbHelper().queryUser().getLoanId();
                    request1.setLoanId(loadId);
                })
                .compose(new SatcatcheResponseTransformer<SaveSalesmanRequest, SaveSalesman>
                        ("saveSalesman"));
    }
}
