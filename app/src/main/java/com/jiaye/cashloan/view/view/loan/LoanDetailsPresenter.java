package com.jiaye.cashloan.view.view.loan;

import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.data.loan.source.LoanDetailsDataSource;

/**
 * LoanDetailsPresenter
 *
 * @author 贾博瑄
 */

public class LoanDetailsPresenter extends BasePresenterImpl implements LoanDetailsContract.Presenter {

    private final LoanDetailsContract.View mView;

    private final LoanDetailsDataSource mDataSource;

    public LoanDetailsPresenter(LoanDetailsContract.View view, LoanDetailsDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }
}
