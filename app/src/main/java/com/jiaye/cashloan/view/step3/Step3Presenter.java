package com.jiaye.cashloan.view.step3;

import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.step3.source.Step3DataSource;

/**
 * Step3Presenter
 *
 * @author 贾博瑄
 */

public class Step3Presenter extends BasePresenterImpl implements Step3Contract.Presenter {

    private final Step3Contract.View mView;

    private final Step3DataSource mDataSource;

    public Step3Presenter(Step3Contract.View view, Step3DataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }
}
