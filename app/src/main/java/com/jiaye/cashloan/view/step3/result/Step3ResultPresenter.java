package com.jiaye.cashloan.view.step3.result;

import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.step3.result.source.Step3ResultDataSource;

import io.reactivex.disposables.Disposable;

/**
 * Step3ResultPresenter
 *
 * @author 贾博瑄
 */

public class Step3ResultPresenter extends BasePresenterImpl implements Step3ResultContract.Presenter {

    private final Step3ResultContract.View mView;

    private final Step3ResultDataSource mDataSource;


    public Step3ResultPresenter(Step3ResultContract.View view, Step3ResultDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void onClickConfirm() {
        /*Disposable disposable = mDataSource.requestUpdateStep()
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
        mCompositeDisposable.add(disposable);*/
    }
}
