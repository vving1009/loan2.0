package com.jiaye.cashloan.view.view.loan.auth.visa;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.base.Request;
import com.jiaye.cashloan.http.data.loan.LoanVisaRequest;
import com.jiaye.cashloan.http.data.loan.LoanVisaSMS;
import com.jiaye.cashloan.http.data.loan.LoanVisaSign;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.loan.auth.source.visa.LoanAuthVisaDataSource;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * LoanAuthVisaPresenter
 *
 * @author 贾博瑄
 */

public class LoanAuthVisaPresenter extends BasePresenterImpl implements LoanAuthVisaContract.Presenter {

    private final LoanAuthVisaContract.View mView;

    private final LoanAuthVisaDataSource mDataSource;

    public LoanAuthVisaPresenter(LoanAuthVisaContract.View view, LoanAuthVisaDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void subscribe() {
        super.subscribe();
        show();
    }

    @Override
    public void sendSMS() {
        Disposable disposable = mDataSource.sendSMS()
                .compose(new ViewTransformer<LoanVisaSMS>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<LoanVisaSMS>() {
                    @Override
                    public void accept(LoanVisaSMS loanVisaSMS) throws Exception {
                        mView.dismissProgressDialog();
                        mView.showSMSDialog();
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void sign(String sms) {
        if (TextUtils.isEmpty(sms)) {
            mView.showToastById(R.string.error_loan_visa_sms);
        } else {
            Disposable disposable = mDataSource.sign(sms)
                    .compose(new ViewTransformer<LoanVisaSign>() {
                        @Override
                        public void accept() {
                            super.accept();
                            mView.showProgressDialog();
                        }
                    })
                    .subscribe(new Consumer<LoanVisaSign>() {
                        @Override
                        public void accept(LoanVisaSign loanVisaSign) throws Exception {
                            mView.dismissProgressDialog();
                            mView.dismissSMSDialog();
                            mView.hideBtn();
                            show();
                        }
                    }, new ThrowableConsumer(mView));
            mCompositeDisposable.add(disposable);
        }
    }

    private void show() {
        Disposable disposable = mDataSource.visa()
                .compose(new ViewTransformer<Request<LoanVisaRequest>>())
                .subscribe(new Consumer<Request<LoanVisaRequest>>() {
                    @Override
                    public void accept(Request<LoanVisaRequest> request) throws Exception {
                        mView.postUrl(BuildConfig.BASE_URL + "show", new Gson().toJson(request).getBytes());
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
