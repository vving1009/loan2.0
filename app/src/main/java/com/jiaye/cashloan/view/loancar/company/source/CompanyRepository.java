package com.jiaye.cashloan.view.loancar.company.source;

import com.jiaye.cashloan.LoanApplication;

import java.util.List;

import io.reactivex.Flowable;

/**
 * CompanyRepository
 *
 * @author 贾博瑄
 */

public class CompanyRepository implements CompanyDataSource {

    @Override
    public Flowable<List<com.jiaye.cashloan.persistence.Salesman>> queryPeople(String column, String value) {
        return Flowable.just(LoanApplication.getInstance().getDbHelper().querySales(column, value));
    }

    @Override
    public Flowable<List<String>> queryCompany() {
        return Flowable.just(LoanApplication.getInstance().getDbHelper().queryCompany());
    }
}

