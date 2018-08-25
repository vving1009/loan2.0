package com.jiaye.cashloan.view.jdcar.family;

import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.jdcar.family.source.FamilyDataSource;

/**
 * FamilyPresenter
 *
 * @author 贾博�?
 */

public class FamilyPresenter extends BasePresenterImpl implements FamilyContract.Presenter {

    private final FamilyContract.View mView;

    private final FamilyDataSource mDataSource;

    public FamilyPresenter(FamilyContract.View view, FamilyDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }
}
