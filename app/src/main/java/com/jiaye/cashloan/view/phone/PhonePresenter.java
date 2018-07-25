package com.jiaye.cashloan.view.phone;

import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.phone.source.PhoneDataSource;

import io.reactivex.disposables.Disposable;

/**
 * PhonePresenter
 *
 * @author 贾博瑄
 */

public class PhonePresenter extends BasePresenterImpl implements PhoneContract.Presenter {

    private final PhoneContract.View mView;

    private final PhoneDataSource mDataSource;

    public PhonePresenter(PhoneContract.View view, PhoneDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void requestUpdatePhone() {
        Disposable disposable = mDataSource.requestUpdatePhone()
                .compose(new ViewTransformer<EmptyResponse>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(emptyResponse -> {
                    mView.dismissProgressDialog();
                    mView.exit();
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
