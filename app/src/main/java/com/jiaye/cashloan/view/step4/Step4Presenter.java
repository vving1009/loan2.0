package com.jiaye.cashloan.view.step4;

import com.jiaye.cashloan.http.data.certification.Step;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.step4.source.Step4DataSource;

import io.reactivex.disposables.Disposable;

/**
 * Step3ResultPresenter
 *
 * @author 贾博瑄
 */

public class Step4Presenter extends BasePresenterImpl implements Step4Contract.Presenter {

    private final Step4Contract.View mView;

    private final Step4DataSource mDataSource;

    public Step4Presenter(Step4Contract.View view, Step4DataSource dataSource) {
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
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
