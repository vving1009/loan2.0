package com.jiaye.cashloan.view.certification;

import com.jiaye.cashloan.http.data.certification.Step;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.certification.source.CertificationDataSource;

import io.reactivex.disposables.Disposable;

/**
 * CertificationPresenter
 *
 * @author 贾博瑄
 */

public class CertificationPresenter extends BasePresenterImpl implements CertificationContract.Presenter {

    private final CertificationContract.View mView;

    private final CertificationDataSource mDataSource;

    public CertificationPresenter(CertificationContract.View view, CertificationDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void subscribe() {
        super.subscribe();
        Disposable disposable = mDataSource.requestRecommend()
                .compose(new ViewTransformer<>())
                .subscribe(recommend -> {
                    mView.setCompany(recommend.getCompany());
                    mView.setName(recommend.getName());
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
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
                    mView.setStep(step.getStep());
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
