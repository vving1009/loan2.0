package com.jiaye.cashloan.view.search.source;

import com.jiaye.cashloan.LoanApplication;
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
}
