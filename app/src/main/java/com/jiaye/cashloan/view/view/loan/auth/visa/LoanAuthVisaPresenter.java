package com.jiaye.cashloan.view.view.loan.auth.visa;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.base.Request;
import com.jiaye.cashloan.http.data.loan.LoanVisaRequest;
import com.jiaye.cashloan.http.data.loan.LoanVisaSMS;
import com.jiaye.cashloan.http.data.loan.Visa;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.loan.auth.source.visa.LoanAuthVisaDataSource;

import org.reactivestreams.Publisher;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * LoanAuthVisaPresenter
 *
 * @author 贾博瑄
 */

public class LoanAuthVisaPresenter extends BasePresenterImpl implements LoanAuthVisaContract.Presenter {

    private final LoanAuthVisaContract.View mView;

    private final LoanAuthVisaDataSource mDataSource;

    private String mType;

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
    public void setType(String type) {
        mType = type;
    }

    @Override
    public void sendSMS() {
        Disposable disposable = mDataSource.sendSMS(mType)
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
            Disposable disposable = mDataSource.sign(mType, sms)
                    .flatMap(new Function<Visa, Publisher<Request<LoanVisaRequest>>>() {
                        @Override
                        public Publisher<Request<LoanVisaRequest>> apply(Visa loanVisaSign) throws Exception {
                            return mDataSource.visa(mType);
                        }
                    })
                    .compose(new ViewTransformer<Request<LoanVisaRequest>>() {
                        @Override
                        public void accept() {
                            super.accept();
                            mView.showProgressDialog();
                        }
                    })
                    .subscribe(new Consumer<Request<LoanVisaRequest>>() {
                        @Override
                        public void accept(Request<LoanVisaRequest> request) throws Exception {
                            mView.dismissProgressDialog();
                            mView.dismissSMSDialog();
                            mView.hideBtn();
                            loadUrl(request);
                        }
                    }, new ThrowableConsumer(mView));
            mCompositeDisposable.add(disposable);
        }
    }

    private void show() {
        Disposable disposable = mDataSource.visa(mType)
                .compose(new ViewTransformer<Request<LoanVisaRequest>>())
                .subscribe(new Consumer<Request<LoanVisaRequest>>() {
                    @Override
                    public void accept(Request<LoanVisaRequest> request) throws Exception {
                        loadUrl(request);
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    private void loadUrl(Request<LoanVisaRequest> request) {
        mView.postUrl(BuildConfig.BASE_URL + "show", new Gson().toJson(request).getBytes());
    }
}
