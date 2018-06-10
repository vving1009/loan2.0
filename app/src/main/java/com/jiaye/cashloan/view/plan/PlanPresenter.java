package com.jiaye.cashloan.view.plan;

import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.plan.source.PlanDataSource;

/**
 * PlanPresenter
 *
 * @author 贾博瑄
 */

public class PlanPresenter extends BasePresenterImpl implements PlanContract.Presenter {

    private final PlanContract.View mView;

    private final PlanDataSource mDataSource;

    public PlanPresenter(PlanContract.View view, PlanDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }
}
