package com.jiaye.cashloan.view.loancredit.step1;

import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.loancredit.step1.source.Step1DataSource;

/**
 * Step1Presenter
 *
 * @author 贾博瑄
 */

public class Step1Presenter extends BasePresenterImpl implements Step1Contract.Presenter {

    private final Step1Contract.View mView;

    private final Step1DataSource mDataSource;

    public Step1Presenter(Step1Contract.View view, Step1DataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }
}
