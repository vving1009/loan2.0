package com.jiaye.cashloan.view.step2.parent;

import com.jiaye.cashloan.http.data.step2.Step2Input;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.step2.parent.source.Step2DataSource;

/**
 * Step1InputPresenter
 *
 * @author 贾博瑄
 */

public class Step2Presenter extends BasePresenterImpl implements Step2Contract.Presenter {

    private final Step2Contract.View mView;

    private final Step2DataSource mDataSource;

    private Step2Input mStep2Input;

    public Step2Presenter(Step2Contract.View view, Step2DataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }
}
