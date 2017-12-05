package com.jiaye.cashloan.view.view.loan;

import com.jiaye.cashloan.view.BasePresenterImpl;

/**
 * LoanProgressPresenter
 *
 * @author 贾博瑄
 */

public class LoanProgressPresenter extends BasePresenterImpl implements LoanProgressContract.Presenter {

    private final LoanProgressContract.View mView;

    public LoanProgressPresenter(LoanProgressContract.View view) {
        mView = view;
    }
}
