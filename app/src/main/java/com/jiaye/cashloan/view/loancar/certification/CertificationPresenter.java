package com.jiaye.cashloan.view.loancar.certification;

import com.jiaye.cashloan.http.data.certification.Step;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.loancar.certification.source.CertificationDataSource;

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
