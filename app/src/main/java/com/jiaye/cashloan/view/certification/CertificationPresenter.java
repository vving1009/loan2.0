package com.jiaye.cashloan.view.certification;

import com.jiaye.cashloan.http.data.certification.Step;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.certification.source.CertificationDataSource;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
    public void requestStep() {
        Disposable disposable = mDataSource.requestStep()
                .compose(new ViewTransformer<Step>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .doOnNext(step -> {
                    if (step.getStep() != 10) {
                        mView.dismissProgressDialog();
                        mView.setStep(step.getStep(), false);
                    }
                })
                .filter(step -> step.getStep() == 10)
                .observeOn(Schedulers.io())
                .flatMap(step -> mDataSource.creditInfo())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(creditInfo -> {
                    mView.dismissProgressDialog();
                    mView.setStep(10, !creditInfo.getBankStatus().equals("01"));
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
