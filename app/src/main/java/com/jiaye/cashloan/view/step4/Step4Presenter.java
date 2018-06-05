package com.jiaye.cashloan.view.step4;

import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.step4.source.Step4DataSource;

/**
 * Step4Presenter
 *
 * @author 贾博瑄
 */

public class Step4Presenter extends BasePresenterImpl implements Step4Contract.Presenter {

    private final Step4Contract.View mView;

    private final Step4DataSource mDataSource;

    public Step4Presenter(Step4Contract.View view, Step4DataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }
}
