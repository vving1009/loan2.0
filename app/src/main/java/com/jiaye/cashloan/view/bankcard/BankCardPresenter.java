package com.jiaye.cashloan.view.bankcard;

import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.bankcard.source.BankCardDataSource;

/**
 * BankCardPresenter
 *
 * @author 贾博瑄
 */

public class BankCardPresenter extends BasePresenterImpl implements BankCardContract.Presenter {

    private BankCardContract.View mView;

    private BankCardDataSource mDataSource;

    public BankCardPresenter(BankCardContract.View view, BankCardDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }
}
