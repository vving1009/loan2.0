package com.jiaye.cashloan.view.step1.parent;

import com.jiaye.cashloan.http.data.step2.Step2Input;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.step1.parent.source.Step1DataSource;

/**
 * Step1InputPresenter
 *
 * @author 贾博瑄
 */

public class Step1Presenter extends BasePresenterImpl implements Step1Contract.Presenter {

    private final Step1Contract.View mView;

    private final Step1DataSource mDataSource;

    private Step2Input mStep2Input;

    public Step1Presenter(Step1Contract.View view, Step1DataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }
}
