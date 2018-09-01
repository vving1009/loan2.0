package com.jiaye.cashloan.view.step3.parent;

import com.jiaye.cashloan.http.data.step2.Step2;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.step3.parent.source.Step3DataSource;

/**
 * Step1InputPresenter
 *
 * @author 贾博瑄
 */

public class Step3Presenter extends BasePresenterImpl implements Step3Contract.Presenter {

    private final Step3Contract.View mView;

    private final Step3DataSource mDataSource;

    private Step2 mStep2;

    public Step3Presenter(Step3Contract.View view, Step3DataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }
}
