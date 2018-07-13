package com.jiaye.cashloan.view.insurance;

import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.insurance.source.InsuranceDataSource;

/**
 * InsurancePresenter
 *
 * @author 贾博瑄
 */

public class InsurancePresenter extends BasePresenterImpl implements InsuranceContract.Presenter {

    private final InsuranceContract.View mView;

    private final InsuranceDataSource mDataSource;

    public InsurancePresenter(InsuranceContract.View view, InsuranceDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }
}
