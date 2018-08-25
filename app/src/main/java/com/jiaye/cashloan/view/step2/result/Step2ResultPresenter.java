package com.jiaye.cashloan.view.step2.result;

import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.step2.result.source.Step2ResultDataSource;

import io.reactivex.disposables.Disposable;

/**
 * Step3ResultPresenter
 *
 * @author 贾博瑄
 */

public class Step2ResultPresenter extends BasePresenterImpl implements Step2ResultContract.Presenter {

    private final Step2ResultContract.View mView;

    private final Step2ResultDataSource mDataSource;


    public Step2ResultPresenter(Step2ResultContract.View view, Step2ResultDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void onClickConfirm() {
        Disposable disposable = mDataSource.requestUpdateStep()
                .compose(new ViewTransformer<EmptyResponse>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(emptyResponse -> {
                    mView.dismissProgressDialog();
                    mView.sendBroadcast();
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
