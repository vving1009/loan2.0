package com.jiaye.cashloan.view.step2;

import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.step2.source.Step2DataSource;

/**
 * Step2Presenter
 *
 * @author 贾博瑄
 */

public class Step2Presenter extends BasePresenterImpl implements Step2Contract.Presenter {

    private final Step2Contract.View mView;

    private final Step2DataSource mDataSource;

    public Step2Presenter(Step2Contract.View view, Step2DataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }
}
