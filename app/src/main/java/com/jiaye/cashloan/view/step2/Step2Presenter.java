package com.jiaye.cashloan.view.step2;

import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.certification.Step;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.step2.source.Step2DataSource;

import io.reactivex.disposables.Disposable;

/**
 * Step2Presenter
 *
 * @author 贾博瑄
 */

public class Step2Presenter extends BasePresenterImpl implements Step2Contract.Presenter {

    private final Step2Contract.View mView;

    private final Step2DataSource mDataSource;

    public Step2Presenter(Step2Contract.View view, Step2DataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void requestStep() {
        Disposable disposable = mDataSource.requestStep()
                .compose(new ViewTransformer<Step>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(step -> {
                    mView.dismissProgressDialog();
                    mView.setText(step.getMsg());
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void onClickNext() {
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
