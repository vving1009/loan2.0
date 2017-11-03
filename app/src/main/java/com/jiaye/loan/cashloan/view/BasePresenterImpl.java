package com.jiaye.loan.cashloan.view;

import com.jiaye.loan.cashloan.view.data.BaseDataSource;

import io.reactivex.disposables.CompositeDisposable;

/**
 * BasePresenterImpl
 *
 * @author 贾博瑄
 */

public abstract class BasePresenterImpl implements BasePresenter {

    protected CompositeDisposable mCompositeDisposable;

    private BaseDataSource mBaseDataSource;

    public BasePresenterImpl(BaseDataSource dataSource) {
        mCompositeDisposable = new CompositeDisposable();
        mBaseDataSource = dataSource;
    }

    @Override
    public void subscribe() {
        if (mBaseDataSource != null) {
            mBaseDataSource.open();
        }
    }

    @Override
    public void unsubscribe() {
        if (mBaseDataSource != null) {
            mBaseDataSource.close();
        }
        mCompositeDisposable.clear();
    }
}
